package se.kth.dd2480;// package

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
 
import java.io.IOException;
import java.nio.file.Files;


import org.eclipse.jgit.api.Git;
import se.kth.dd2480.utils.GitUtilities;
import org.apache.log4j.BasicConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.webapp.WebAppContext;
import org.json.JSONObject;
import org.kohsuke.github.GHEventPayload.Create;

import java.util.stream.Collectors;
import java.util.Date;
import java.text.*;
import org.json.*;
import java.nio.file.*;



/** 
 Skeleton of a com.kth.dd2480.ContinuousIntegrationServer which acts as webhook
 See the Jetty documentation for API documentation of those classes.
*/
public class ContinuousIntegrationServer extends AbstractHandler
{
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) 
        throws IOException, ServletException
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        System.out.println(target);

        // here you do all the continuous integration tasks
        // for example
        


        // 1st clone your repository
        Git clone = GitUtilities.cloneRepository("https://github.com/NicoleWij/Group_5_Assignment_1");

        // 2nd compile the code

        if (clone != null) {
            Path directory = clone.getRepository().getDirectory().toPath().getParent();
            boolean success = GitUtilities.compileProject(directory);
            if (success) {
                System.out.println("Compilation successful");
            } else {
                System.out.println("Compilation failed");
            }
        }


        response.getWriter().println("CI job done");

    }
 
    // used to start the CI server in command line
    public static void main(String[] args) throws Exception
    {
        BasicConfigurator.configure();
        Server server = new Server(8005);
        server.setHandler(new ContinuousIntegrationServer()); 
        server.start();
        server.join();
    }
}
