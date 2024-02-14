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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.Test;
import se.kth.dd2480.utils.GitUtilities;


class AppTest {
    @Test
    void appHasAGreeting() {
        // App classUnderTest = new App();
        // assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
    }

    @Test
    void testUpdateStatus() throws IOException {

        String status = "success";
        GithubStatusUpdater.updateStatus("NicoleWij", "Group_5_Assignment_2", "c6c4d82fc9793c14c8af0183c5047d54960b791d", status);
        Assert.assertEquals(GithubStatusUpdater.getStatus("NicoleWij", "Group_5_Assignment_2", "c6c4d82fc9793c14c8af0183c5047d54960b791d"), status);

        status = "failure";
        GithubStatusUpdater.updateStatus("NicoleWij", "Group_5_Assignment_2", "c6c4d82fc9793c14c8af0183c5047d54960b791d", status);
        Assert.assertEquals(GithubStatusUpdater.getStatus("NicoleWij", "Group_5_Assignment_2", "c6c4d82fc9793c14c8af0183c5047d54960b791d"), status);

    }
}
