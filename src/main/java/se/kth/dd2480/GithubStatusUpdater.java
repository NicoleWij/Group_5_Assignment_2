package se.kth.dd2480;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

/**
 * The GithubStatusUpdater class provides methods for updating the status of a commit on GitHub.
 * It includes functionality to send a status request to the GitHub API and update the commit status
 * based on the provided parameters.
 */
public class GithubStatusUpdater {

    /**
     * Sends a status request to the GitHub API to update the status of a commit.
     *
     * @param owner  The owner of the GitHub repository.
     * @param repo   The name of the GitHub repository.
     * @param sha    The SHA-1 hash of the commit.
     * @param token  The authentication token for accessing the GitHub API.
     * @param status The status to set for the commit ("success", "failure", "pending", or "error").
     * @throws IOException if there is an issue with the HTTP request or response.
     */
    public static void requestStatus(String owner, String repo, String sha, String token, String status)

            throws IOException {
        OkHttpClient client = new OkHttpClient();

        // Create JSON payload for the status request
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("state", status); // success or failure or pending or error
        jsonBody.put("target_url", "https://example.com/build/status");

        // Set description based on the status
        if (status.equals("success")) {
            jsonBody.put("description", "Tests passed!");
        } else if (status.equals("failure")) {
            jsonBody.put("description", "Tests failed!");
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
            return true;
        }catch (Exception e){
            System.out.println("False case");
            return false;
        }
    }

    /**
     * Retrieves the GitHub authentication token from the environment variables.
     *
     * @return The GitHub authentication token.
     */
    public static String getAuthToken() {
        String token = System.getenv("GITHUB_AUTH");
        return token;
    }


    /**
     * Updates the status of a commit on GitHub using the provided parameters.
     *
     * @param owner  The owner of the GitHub repository.
     * @param repo   The name of the GitHub repository.
     * @param sha    The SHA-1 hash of the commit.
     * @param status The status to set for the commit ("success", "failure", "pending", or "error").
     * @throws IOException if there is an issue with the HTTP request or response.
     */
    public static void updateStatus(String owner, String repo, String sha, String status) throws IOException {

        String token = getAuthToken();
        return GithubStatusUpdater.requestStatus(owner, repo, sha, token, status);
    }



    public static String getStatus(String owner, String repo, String sha){
        String token =  getAuthToken();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/repos/" + owner + "/" + repo + "/commits/" + sha + "/status")
                .header("Accept", "application/vnd.github+json")
                .header("Authorization", "Bearer " + token)
                .header("X-GitHub-Api-Version", "2022-11-28")
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to update commit status: " + response);
            }
            JSONObject res = new JSONObject(response.body().string());
            String state = res.getString("state");
            return state;
        }catch (Exception e){
            return null;
        }
    }



}


