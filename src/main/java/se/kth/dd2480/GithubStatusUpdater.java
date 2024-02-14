package se.kth.dd2480;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class GithubStatusUpdater {

    public static void requestStatus(String owner, String repo, String sha, String token, String status)
            throws IOException {
        OkHttpClient client = new OkHttpClient();

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("state", status); // success or failure or pending or error
        jsonBody.put("target_url", "https://example.com/build/status");

        if (status.equals("success")) {
            jsonBody.put("description", "Tests passed!");
        } else if (status.equals("failure")) {
            jsonBody.put("description", "Tests failed!");
        } else if (status.equals("pending")) {
            jsonBody.put("description", "Status pending.");
        } else if (status.equals("error")) {
            jsonBody.put("description", "Error");
        }

        jsonBody.put("context", "continuous-integration/jenkins");

        // Create a request body with the JSON payload
        RequestBody body = RequestBody.create(jsonBody.toString(), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url("https://api.github.com/repos/" + owner + "/" + repo + "/statuses/" + sha)
                .header("Accept", "application/vnd.github+json")
                .header("Authorization", "Bearer " + token)
                .header("X-GitHub-Api-Version", "2022-11-28")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to update commit status: " + response);
            }
            System.out.println(response.body().string());

            System.out.println("Commit status updated successfully");
        }
    }

    public static String getAuthToken() {
        String token = System.getenv("GITHUB_AUTH");
        return token;
    }

    public static void updateStatus(String owner, String repo, String sha, String status) throws IOException {
        String token = getAuthToken();
        GithubStatusUpdater.requestStatus(owner, repo, sha, token, status);
    }

}