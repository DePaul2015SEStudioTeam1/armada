/**
 * 
 */
package edu.depaul.armada;

import java.net.URL;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
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

	public static void main(String[] args) throws Exception {
		
		Server server = new Server(8083);
		WebAppContext root = new WebAppContext();
		
		root.setContextPath("/");
		root.setParentLoaderPriority(true);
		
		server.setHandler(root);
		
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);
		resourceHandler.setWelcomeFiles(new String[]{"index.html"});
		
		ClassLoader loader = Armada.class.getClassLoader();
		URL resource = loader.getResource("assets/");
		String webDir = resource.toExternalForm();
		
		resourceHandler.setResourceBase(webDir);
		
		ServletHandler servletHandler = new ServletHandler();
		DispatcherServlet dispatcherServlet = new DispatcherServlet();
		dispatcherServlet.setContextConfigLocation("classpath:/beans/armada-config.xml");
		ServletHolder servletHolder = new ServletHolder(dispatcherServlet);
		servletHandler.addServlet(servletHolder);
		
		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] {resourceHandler, servletHandler, new DefaultHandler()});
		server.setHandler(handlers);
		server.start();
		server.join();
	}

}
