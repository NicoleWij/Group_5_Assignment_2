package se.kth.dd2480.utils;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.json.JSONObject;
import se.kth.dd2480.GithubStatusUpdater;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A utility class that provides methods for interacting with Git repositories,
 * including cloning and checking out commits, compiling and testing Maven projects,
 * and handling webhook requests from GitHub.
 */
public class GitUtilities {

    /**
     * Clones a Git repository from a specified URL into a given directory and checks out a specific commit.
     *
     * @param url    The URL of the Git repository to clone.
     * @param path   The directory where the repository should be cloned.
     * @param commit The commit hash to check out after cloning.
     * @return A {@link Git} instance representing the cloned repository.
     * @throws IOException If an I/O error occurs during the cloning process.
     */
    public static Git cloneRepository(String url, File path, String commit) throws IOException {
        System.out.println("Cloning repository: " + url);
        // If the directory already exists, delete it
        if (path.exists()) {
            Files.walk(path.toPath())
                    .map(Path::toFile)
                    .sorted((o1, o2) -> -o1.compareTo(o2))
                    .forEach(File::delete);
        }
        try {
            Git repo = Git.cloneRepository()
                    .setURI(url)
                    .setDirectory(path)
                    .call();
            repo.checkout().setName(commit).call();
            return repo;
        } catch (GitAPIException e) {
            return null;
        }
    }

    /**
     * Core CI feature #1 - compilation
     * Compiles a Maven project located at a given path.
     *
     * @param path The path to the directory containing the Maven project to be compiled.
     * @return {@code true} if compilation was successful, {@code false} otherwise
     */
    public static boolean compileProject(File path) {
        Path projectDir = path.toPath();
        try {
            // Assuming Maven project
            ProcessBuilder builder = new ProcessBuilder();
            builder.command("mvn", "-f", projectDir.resolve("pom.xml").toString(), "compile");
            Process process = builder.start();
            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            System.err.println("Exception occurred during project compilation: " + e.getMessage());
            return false;
        }
    }

    /**
     * Core CI feature #2 - testing
     * Tests a Maven project located at a given path.
     *
     * @param path The path to the directory containing the Maven project to be tested.
     * @return {@code true} if all tests pass, {@code false} otherwise.
     */
    public static boolean testProject(File path) {
        try {
            ProcessBuilder testBuilder = new ProcessBuilder();
            testBuilder.command("mvn", "test");
            testBuilder.directory(path);
            testBuilder.redirectErrorStream(true);
            Process runTest = testBuilder.start();
            int exitCode = runTest.waitFor();
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            System.err.println("Exception occurred during project testing: " + e.getMessage());
            return false;
        }
    }

    /**
     * Handles a webhook request from GitHub. This method is called when a push
     * event is received from GitHub. It automatically compiles and tests the
     * project and updates the commit status on GitHub.
     *
     * @param request  The {@link HttpServletRequest} object representing the incoming webhook request.
     * @param response The {@link HttpServletResponse} object for sending responses to the request.
     * @throws IOException If an I/O error occurs while reading the request or sending the response.
     */
    public static void handleWebhook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!request.getMethod().equals("POST"))
            throw new AssertionError("Expected POST request");
        if (!request.getContentType().equals("application/json"))
            throw new AssertionError("Expected JSON content type");

        // Get the JSON payload from the request
        StringBuilder payload = new StringBuilder();
        request.getReader().lines().forEach(payload::append);
        String body = payload.toString();

        JSONObject json = new JSONObject(body);
        System.out.println("Received webhook: " + json.toString());
        String ref = json.getString("ref");
        String after = json.getString("after");

        if (!isAssessmentBranch(ref)) {
            System.out.println("Not the assessment branch, skipping");
            return;
        }

        File path = new File("res/tmp/");
        String status;

        cloneRepository("https://github.com/" + json.getJSONObject("repository").getString("full_name") + ".git",
                path,
                after);

        boolean success = compileProject(path);
        String pusher = json.getJSONObject("pusher").getString("name");
        String timestamp = json.getJSONObject("head_commit").getString("timestamp");
        StringBuilder buildLogs = new StringBuilder();
        buildLogs.append("<p>Build triggered by ")
                .append(pusher).append("</p>");

        if (success) {
            System.out.println("Compilation successful");
            buildLogs.append("<p style=\"color:darkgreen\"> Compilation : " +
                    "Success</p>");
        } else {
            System.out.println("Compilation failed");
            buildLogs.append("<p style=\"color:firebrick\"> Compilation : " +
                    "Failed</p>");
            status = "error";
            GithubStatusUpdater.updateStatus("NicoleWij", "Group_5_Assignment_2", after, status);
        }

        if (success) {
            boolean testsPassed = testProject(path);

            if (testsPassed) {
                System.out.println("Tests passed");
                buildLogs.append("<p style=\"color:darkgreen\"> Tests : " +
                        "Success</p>");
                status = "success";
            } else {
                System.out.println("Tests failed");
                buildLogs.append("<p style=\"color:firebrick\"> Tests : " +
                        "Failed</p>");
                status = "failure";
            }
            GithubStatusUpdater.updateStatus("NicoleWij", "Group_5_Assignment_2", after, status);
        }
        BuildHistory.addBuild(after, timestamp, buildLogs.toString());
        BuildHistory.addBuildToHistoryList(after, timestamp, buildLogs.toString());
    }

    /**
     * Determines whether the specified Git reference corresponds to the "assessment" branch.
     *
     * @param ref The Git Reference of the push event
     * @return {@code true} if the reference corresponds to the "assessment"
     * branch, {@code false} otherwise
     */
    public static boolean isAssessmentBranch(String ref) {
        try {
            return ref.trim().split("/")[2].equals("assessment");
        } catch (Exception e) {
            return false;
        }
    }
}
