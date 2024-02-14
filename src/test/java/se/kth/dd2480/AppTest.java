package se.kth.dd2480;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


import static org.junit.jupiter.api.Assertions.*;
import org.eclipse.jgit.api.Git;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.Test;
import se.kth.dd2480.utils.GitUtilities;


class AppTest {
    @Test
    void testCloning() throws IOException {
        // Success Case. We clone a repository and save it to specified path. Function returns Git object when true.
        File path = new File("res/tmp/");
        String url = "https://github.com/NicoleWij/Group_5_Assignment_1.git";
        String commit = "05e9eed391f355b0f93b711ffd574b8b91971813";
        Assert.assertNotEquals(null, GitUtilities.cloneRepository(url, path, commit));

        // Failure Case. Function returns null when unable to find repo
        path = new File("res/tmp/");
        url = "https://github.com/NicoleWij/Group_5_Assignment_1.git";
        commit = "fgsrbg05e9eed391f355b0f93b";
        Assert.assertEquals(null, GitUtilities.cloneRepository(url, path, commit));
    }

    @Test
    void testUpdateStatus() throws IOException {
        // Success case. We just update a sample commit's status and make another HTTP request to check if we changed it correctly or not.
        String status = "success";
        GithubStatusUpdater.updateStatus("NicoleWij", "Group_5_Assignment_2", "c6c4d82fc9793c14c8af0183c5047d54960b791d", status);
        Assert.assertEquals(GithubStatusUpdater.getStatus("NicoleWij", "Group_5_Assignment_2", "c6c4d82fc9793c14c8af0183c5047d54960b791d"), status);
        // Failure case. We update the same commit's status to see if it's modified or not.
        status = "failure";
        GithubStatusUpdater.updateStatus("NicoleWij", "Group_5_Assignment_2", "c6c4d82fc9793c14c8af0183c5047d54960b791d", status);
        Assert.assertEquals(GithubStatusUpdater.getStatus("NicoleWij", "Group_5_Assignment_2", "c6c4d82fc9793c14c8af0183c5047d54960b791d"), status);
    }

    @Test
    void testCompiling() throws IOException {
        // False case. Testing the compileProject function with a path that doesn't exist.
        // Function returns true if the repository could be compiled, false otherwise.
        File path = new File("res/tmp/asdfjdsk/");
        Assert.assertFalse(GitUtilities.compileProject(path));

        // True case. Testing with the correct path after cloning the project. Note: Assumption has been made that
        // clone project testing will be conducted before this test.
        path = new File("res/tmp/");
        Assert.assertTrue(GitUtilities.compileProject(path));
    }
}
