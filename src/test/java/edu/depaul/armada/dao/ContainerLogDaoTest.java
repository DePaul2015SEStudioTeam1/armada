/**
 * 
 */
package edu.depaul.armada.dao;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.depaul.armada.domain.Container;
import edu.depaul.armada.domain.ContainerLog;

/**
 * @author jplante
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beans/armada-config-test.xml"})
@TransactionConfiguration(transactionManager="armadaTransactionManager")
@Transactional
public class ContainerLogDaoTest {
 
	@Autowired private ContainerDao containerDao;
	@Autowired private ContainerLogDao containerLogDao;
	
	@Test
	public void testFindWithContainerId() {
		
		Container container = newContainer();
		ContainerLog[] logs = new ContainerLog[3];
		for(int i=0; i<logs.length; i++) {
			logs[i] = newContainerLog();
			container.addLog(logs[i]);
		}
		
		containerDao.store(container);
		
		List<ContainerLog> result = containerLogDao.findWithContainerId(container.getId());
		
		assertEquals(logs.length, result.size());
		assertEquals(logs[0].getId(), result.get(0).getId());
	}

	private Container newContainer() {
		Container container = new Container();
		container.setCAdvisorURL("http://localhost:8080/cAdvisor");
		container.setContainerUniqueId(UUID.randomUUID().toString());
		container.setName("test-name");
		return container;
	}
	
	private ContainerLog newContainerLog() {
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
	
	@Test
	public void testGetContainerLogAvgMemUsage_no_matching_container_log() {
		double average = containerLogDao.getContainerLogAvgMemUsage(-1);
		assertEquals("Expected average to be 0, but was " + average + "!", 0, average, 0.1);
	}
	
	@Test
	public void testGetContainerLogAvgMemUsage_matching_container_logs() {
		
		Container container = newContainer();
		ContainerLog log = newContainerLog();
		container.addLog(log);
	
		containerDao.store(container);	
		double average = containerLogDao.getContainerLogAvgMemUsage(container.getId());
		
		assertEquals("Expected average to be " + log.getMemUsed() + ", but was " + average + "!", log.getMemUsed(), average, 0.1);
		
		container = containerDao.findWithContainerId(container.getId());
		ContainerLog log2 = newContainerLog();
		container.addLog(log2);
		containerDao.store(container);
		
		List<ContainerLog> logs = containerLogDao.findWithContainerId(container.getId());
		assertEquals(2, logs.size());
		
		average = containerLogDao.getContainerLogAvgMemUsage(container.getId());
		double expectedAverage = ((log.getMemUsed() + log2.getMemUsed())/2d);
		assertEquals("Expected average to be " + expectedAverage + ", but was " + average + "!", expectedAverage, average, 0.1);
	}
}
