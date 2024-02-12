package se.kth.dd2480.utils;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GitUtilities {
    public static Git cloneRepository(String url) throws IOException {
        Path tempDir = Files.createTempDirectory("myRepository");
        Path pathDir = tempDir.toAbsolutePath();
        String cloneDirectoryPath = pathDir.toString();

        try {
            return Git.cloneRepository()
                    .setURI(url)
                    .setDirectory(new File("res/tmp/"))
                    .call();
        } catch (GitAPIException e) {
            System.err.println("Exception occurred while cloning repo: " + e.getMessage());
            return null;
        }
    }

    public static boolean compileProject(Path projectDir) {
        try {
            // Assuming Maven project, replace with your build command
            ProcessBuilder builder = new ProcessBuilder();
            builder.command("mvn", "-f", projectDir.resolve("pom.xml").toString(), "compile");
            builder.directory(projectDir.toFile());
            Process process = builder.start();
            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            System.err.println("Exception occurred during project compilation: " + e.getMessage());
            return false;
        }
    }

}
