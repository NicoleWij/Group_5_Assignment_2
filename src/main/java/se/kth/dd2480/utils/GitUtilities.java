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
}
