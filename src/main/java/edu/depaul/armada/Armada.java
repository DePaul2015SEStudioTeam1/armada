/**
 * 
 */
package edu.depaul.armada;

import java.net.URL;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Main entry point into the armada app
 * 
 * @author ptrzyna and jplante
 */
public class Armada {
	private static boolean status;
	private Server server;
	
	/**
	 * The main method is the only method in the Armada class. Its responsibilities are to define what resources will be used, 
	 * including defining index.html as the homepage of the application when running. It also sets up and starts the web server 
	 * from which the application runs.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Armada starter = new Armada();
		starter.startServer(args);
	}
	
	public boolean startServer(String[] args) throws Exception {
		server = new Server(8083);
		
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);
		resourceHandler.setWelcomeFiles(new String[]{"index.html"});
		
		// loads dashboard from classpath
		ClassLoader loader = Armada.class.getClassLoader();
		URL resource = loader.getResource("assets/");
		String webDir = resource.toExternalForm();
		
		resourceHandler.setResourceBase(webDir);
		
		ServletHandler servletHandler = new ServletHandler();
		DispatcherServlet dispatcherServlet = new DispatcherServlet();
		dispatcherServlet.setContextConfigLocation("classpath:/beans/armada-config.xml");
		ServletHolder servletHolder = new ServletHolder(dispatcherServlet);
		servletHandler.addServlet(servletHolder);
		
		WebAppContext webApp = new WebAppContext();
		webApp.setResourceBase(webDir);
		webApp.setContextPath("/");
		webApp.addServlet(servletHolder, "/");
		
		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] {resourceHandler, webApp});
		server.setHandler(handlers);
		
		server.start();
		server.join();
		
		System.out.println("Server started");
		
		return server.isRunning();
	}
	
	public boolean stopServer() throws Exception {
		server.stop();
		System.out.println("Server stopped");
		return server.isStopped();
	}

}
