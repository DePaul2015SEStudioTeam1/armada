/**
 * 
 */
package edu.depaul.armada;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main entry point into the armada app
 * 
 * @author ptrzyna
 */
public class Armada {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans/armada-config.xml");
	}

}
