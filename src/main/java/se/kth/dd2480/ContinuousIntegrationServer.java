package se.kth.dd2480;// package

import org.json.JSONObject;

import se.kth.dd2480.utils.BuildHistory;
import se.kth.dd2480.utils.GitUtilities;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.util.Map;


/**
 * Skeleton of a com.kth.dd2480.ContinuousIntegrationServer which acts as webhook
 * See the Jetty documentation for API documentation of those classes.
 */
public class ContinuousIntegrationServer extends AbstractHandler {
    /**
     * This method is called when the server receives a request. It should
     * handle the request and provide a response.
     *
     * @param target      The target of the request - a URI
     * @param baseRequest The original unwrapped request object.
     * @param request     The request either as the {@link Request}
     * @param response    The response as the {@link HttpServletResponse}
     */
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);


        if (target.equals("/")) {
            response.getWriter().println("This is the CI server");
            GitUtilities.handleWebhook(request, response);
        } else if (target.matches("/builds/\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2" +
                "}:\\d{2}\\+\\d{2}:\\d{2}")) {
            showSingleBuild(target.substring(8), response);
        } else if (target.equals("/builds")) {
            showAllBuilds(response);
        } else {
            response.getWriter().println("This is not the page you are looking for");
        }
    }

    /**
     * This method is called when the server receives a request to show a single
     * build. It should handle the request and provide a response.
     *
     * @param buildName the name of the build to show
     * @param response  the response as the {@link HttpServletResponse}
     * @throws IOException if an I/O error occurs while reading the request or
     *                     sending the response.
     */
    public static void showSingleBuild(String buildName,
                                       HttpServletResponse response)
            throws IOException {
        JSONObject build = BuildHistory.getBuild(buildName);
        if (build == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().println("Build not found");
        } else {
            response.getWriter().println("<a href=\"/builds\">Back to builds</a>");
            response.getWriter().println("<h1>Build " + buildName + "</h1>");
            response.getWriter().println("<p>Commit name : " + build.get(
                    "commit") + "</p>");
            response.getWriter().println("<p>Build date is : " + build.get(
                    "date") + "</p>");
            response.getWriter().println("<p>Logs : " + build.get("logs") + "</p>");
        }
    }

    /**
     * This method is called when the server receives a request to show all
     * builds. It should handle the request and provide a response.
     *
     * @param response the response as the {@link HttpServletResponse}
     * @throws IOException if an I/O error occurs while reading the request or
     *                     sending the response.
     */
    public static void showAllBuilds(HttpServletResponse response)
            throws IOException {
        Map<String, JSONObject> builds = BuildHistory.getBuildHistoryList();
        response.getWriter().println("<h1>Build list :</h1>");
        for (String buildName : builds.keySet()) {
            if (buildName.equals("builds")) {
                continue;
            }
            response.getWriter().println("<p><a href=\"builds/" + buildName +
                    "\">" +
                    buildName + "</a></p>");
        }
    }

    /**
     * Used to start the server.
     */
    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();
        Server server = new Server(8005);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();
    }
}
