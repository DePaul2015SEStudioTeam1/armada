/**
 * 
 */
package edu.depaul.armada;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main entry point into the armada app
 * 
 * @author ptrzyna
 */
public class Armada {

	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans/armada-config.xml");
		
		String webappDirLocation = "src/main/webapp/";
		
		Server server = new Server(8083);
		WebAppContext root = new WebAppContext();
		
		root.setContextPath("/");
		root.setDescriptor(webappDirLocation + "WEB-INF/web.xml");
		root.setResourceBase(webappDirLocation);
		root.setParentLoaderPriority(true);
		server.setHandler(root);
		
		ResourceHandler resource_handler = new ResourceHandler();
		resource_handler.setDirectoriesListed(true);
		resource_handler.setWelcomeFiles(new String[]{"index.html"});
		resource_handler.setResourceBase(".");
		
		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] {resource_handler, new DefaultHandler()});
		server.setHandler(handlers);
		
		server.start();
		server.join();
	}

}
