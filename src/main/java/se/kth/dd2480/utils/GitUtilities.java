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
import java.util.stream.Collectors;

public class GitUtilities {

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
     *
     * @param path - path to the project
     * @return true if compilation was successful, false otherwise
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
     *
     * @param path - path to the project
     * @return true if tests were successful, false otherwise
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

        if (success) {
            System.out.println("Compilation successful");
        } else {
            System.out.println("Compilation failed");
            status = "error";
            GithubStatusUpdater.updateStatus("NicoleWij", "Group_5_Assignment_2", after, status );
        }

        if (success) {
            boolean testsPassed = testProject(path);

            if (testsPassed) {
                System.out.println("Tests passed");
                status = "success";
            } else {
                System.out.println("Tests failed");
                status = "failure";
            }
            GithubStatusUpdater.updateStatus("NicoleWij", "Group_5_Assignment_2", after, status );
        }
    }

    public static boolean isAssessmentBranch(String ref) {
        try {
            return ref.trim().split("/")[2].equals("assessment");
        } catch (Exception e) {
            return false;
        }
    }
}
