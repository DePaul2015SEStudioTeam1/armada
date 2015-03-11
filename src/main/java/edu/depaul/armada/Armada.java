/*
 * The MIT License (MIT)
 * 
 * Copyright (c) <year> <copyright holders> 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
	
	public void startServer(String[] args) throws Exception {
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
	}
}
