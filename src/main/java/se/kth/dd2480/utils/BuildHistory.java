package se.kth.dd2480.utils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to store the build history of the project.
 * This history persists even if the server is rebooted. Each build is given a
 * unique URL, that is accessible to get the build information (commit
 * identifier, build date, build logs).
 */
public final class BuildHistory {
    /**
     * This class should not be instantiated.
     */
    private BuildHistory() {
        throw new IllegalStateException("Utility class cannot be instantiated.");
    }

    /**
     * The path to the build history file.
     */
    private static final String BUILD_HISTORY_PATH = "res/builds/";
    private static final String BUILD_HISTORY_LIST = "res/builds.json";

    /**
     * Adds a build to the build history.
     *
     * @param commit The commit identifier.
     * @param date   The date of the build.
     * @param logs   Build logs.
     */
    public static void addBuild(String commit, String date, String logs) {
        JSONObject build = new JSONObject();
        build.put("commit", commit);
        build.put("date", date);
        build.put("logs", logs);

        // Save the build to a file
        String filename = BUILD_HISTORY_PATH + date + ".json";
        // Create the directory if it does not exist
        try {
            java.nio.file.Files.createDirectories(java.nio.file.Paths.get(BUILD_HISTORY_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Write the build to the file
        try (FileWriter file = new FileWriter(filename)) {
            file.write(build.toString());
            System.out.println("Successfully wrote build to " + filename);
            System.out.println("Build logs: " + build);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addBuildToHistoryList(String commit, String date, String logs) {
        JSONObject build = new JSONObject();
        build.put("commit", commit);
        build.put("date", date);
        build.put("logs", logs);
        // Add the build to the list of builds
        String filename = BUILD_HISTORY_LIST;
        File file = new File(filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        // Read the current list of builds
        JSONObject buildList = new JSONObject();
        if (file.length() == 0) {
            buildList.put("builds", new JSONObject());
        } else {
            try (java.util.stream.Stream<String> stream = java.nio.file.Files.lines(java.nio.file.Paths.get(filename))) {
                buildList = new JSONObject(stream.collect(java.util.stream.Collectors.joining()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Add the new build to the list
        buildList.put(date, build);

        // Write the updated list to the file
        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write(buildList.toString());
            System.out.println("Successfully wrote build to " + filename);
            System.out.println("Build logs: " + build);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, JSONObject> getBuildHistoryList() {
        String filename = BUILD_HISTORY_LIST;
        File file = new File(filename);
        if (!file.exists()) {
            return new HashMap<>();
        }
        JSONObject buildList = getJsonObject(filename);
        Map<String, JSONObject> builds = new HashMap<>();
        for (String buildName : buildList.keySet()) {
            builds.put(buildName, buildList.getJSONObject(buildName));
        }
        return builds;
    }

    public static JSONObject getBuild(String build) {
        String filename = BUILD_HISTORY_PATH + build + ".json";
        File file = new File(filename);
        if (!file.exists()) {
            return null;
        }
        return getJsonObject(filename);
    }

    @NotNull
    private static JSONObject getJsonObject(String filename) {
        try (java.util.stream.Stream<String> stream = java.nio.file.Files.lines(java.nio.file.Paths.get(filename))) {
            return new JSONObject(stream.collect(java.util.stream.Collectors.joining()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
