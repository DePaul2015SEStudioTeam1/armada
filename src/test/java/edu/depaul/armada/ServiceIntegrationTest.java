/**
 * 
 */
package edu.depaul.armada;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.depaul.armada.model.Container;
import edu.depaul.armada.service.OperationsService;

/**
 * @author ptrzyna
 */
public class ServiceIntegrationTest {

	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("beans/operations-client.xml");
		OperationsService<Container> service = context.getBean("remoteOperationsService", OperationsService.class);
		
		Container test = new Container();
		test.setAgentId("test");
		for(int i=0; i<11; i++) {
			service.store(test);
		}
		
	}
	
}
