package se.kth.dd2480;// package

import org.apache.commons.io.IOUtils;
import se.kth.dd2480.utils.GitUtilities;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.apache.log4j.BasicConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.nio.file.*;
import java.util.Map;


/**
 Skeleton of a com.kth.dd2480.ContinuousIntegrationServer which acts as webhook
 See the Jetty documentation for API documentation of those classes.
*/
public class ContinuousIntegrationServer extends AbstractHandler
{


    /**
     * @param target
     * @param baseRequest
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
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
        } else {
            response.getWriter().println("This is not the page you are looking for");
        }
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
