package se.kth.dd2480;// package

import se.kth.dd2480.utils.GitUtilities;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.apache.log4j.BasicConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

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

        if (target.equals("/")) {
            response.getWriter().println("This is the CI server");
            GitUtilities.handleWebhook(request, response);
            return;
        }


        // here you do all the continuous integration tasks
        // for example


//        File path = new File("res/tmp/");

//        // 1st clone your repository
//        Git clone = GitUtilities.cloneRepository("https://github.com/NicoleWij/Group_5_Assignment_1", path);
//
//        // 2nd compile the code
//        if (clone != null) {
//            Path directory = clone.getRepository().getDirectory().toPath().getParent();
//            boolean success = GitUtilities.compileProject(path);
//            if (success) {
//                System.out.println("Compilation successful");
//            } else {
//                System.out.println("Compilation failed");
//            }
//        }

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
