package edu.depaul.armada;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.depaul.armada.model.AgentContainerLog;
import edu.depaul.armada.service.ArmadaService;

/**
 * @author ptrzyna
 */
public class ServiceIntegrationTest {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans/armada-client.xml");
		ArmadaService service = context.getBean("remoteArmadaService", ArmadaService.class);
		
		AgentContainerLog test = new AgentContainerLog();
		test.name = "test";
		for(int i=0; i<11; i++) {
			service.send(test);
		}
	}
}
