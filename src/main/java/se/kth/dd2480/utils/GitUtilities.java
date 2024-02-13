package se.kth.dd2480.utils;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GitUtilities {
    public static Git cloneRepository(String url, File path) throws IOException {
        System.out.println("Cloning repository: " + url);
        // If the directory already exists, delete it
        if (path.exists()) {
            Files.walk(path.toPath())
                    .map(Path::toFile)
                    .sorted((o1, o2) -> -o1.compareTo(o2))
                    .forEach(File::delete);
        }
        try {
            return Git.cloneRepository()
                    .setURI(url)
                    .setDirectory(path)
                    .call();
        } catch (GitAPIException e) {
            System.err.println("Exception occurred while cloning repo: " + e.getMessage());
            return null;
        }
    }

    public static boolean compileProject(File path) {
        Path projectDir = path.toPath();
        try {
            // Assuming Maven project, replace with your build command
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
}
