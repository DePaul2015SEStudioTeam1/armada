/**
 * 
 */
package edu.depaul.armada.util;

import java.sql.Timestamp;
import java.util.UUID;

import org.apache.commons.lang3.RandomUtils;

import edu.depaul.armada.domain.Container;
import edu.depaul.armada.domain.ContainerLog;

/**
 * Test utility that can be used to easily set up test objects
 * 
 * @author ptrzyna
 */
public class TestUtil {

	public static Container newContainer() {
		Container container = new Container();
		container.setCAdvisorURL("http://localhost:8080/cAdvisor");
		container.setContainerUniqueId(UUID.randomUUID().toString());
		container.setName("test-name");
		return container;
	}
	
	public static ContainerLog newContainerLog() {
		ContainerLog log = new ContainerLog();
		log.setTimestamp(new Timestamp(0));
		log.setCpuUsed(RandomUtils.nextLong(0, 100));
		log.setCpuTotal(100);
		log.setMemUsed(RandomUtils.nextLong(0, 100));
		log.setMemTotal(100);
		log.setDiskUsed(RandomUtils.nextLong(0, 100));
		log.setDiskTotal(100);
		return log;
	}
	
}
