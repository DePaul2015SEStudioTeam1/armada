/**
 * 
 */
package edu.depaul.armada.dao;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.depaul.armada.domain.Container;
import edu.depaul.armada.domain.ContainerLog;
import edu.depaul.armada.util.TestUtil;

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
		
		Container container = TestUtil.newContainer();
		ContainerLog[] logs = new ContainerLog[3];
		for(int i=0; i<logs.length; i++) {
			logs[i] = TestUtil.newContainerLog();
			container.addLog(logs[i]);
		}
		
		containerDao.store(container);
		
		List<ContainerLog> result = containerLogDao.findWithContainerId(container.getId());
		
		assertEquals(logs.length, result.size());
		assertEquals(logs[0].getId(), result.get(0).getId());
	}
	
	@Test
	public void testGetContainerLogAvgMemUsage_no_matching_container_log() {
		double average = containerLogDao.getContainerLogAvgMemUsage(-1);
		assertEquals("Expected average to be 0, but was " + average + "!", 0, average, 0.1);
	}
	
	@Test
	public void testGetContainerLogAvgMemUsage_matching_container_logs() {
		
		Container container = TestUtil.newContainer();
		ContainerLog log = TestUtil.newContainerLog();
		container.addLog(log);
	
		containerDao.store(container);	
		double average = containerLogDao.getContainerLogAvgMemUsage(container.getId());
		
		assertEquals("Expected average to be " + log.getMemUsed() + ", but was " + average + "!", log.getMemUsed(), average, 0.1);
		
		container = containerDao.findWithContainerId(container.getId());
		ContainerLog log2 = TestUtil.newContainerLog();
		container.addLog(log2);
		containerDao.store(container);
		
		List<ContainerLog> logs = containerLogDao.findWithContainerId(container.getId());
		assertEquals(2, logs.size());
		
		average = containerLogDao.getContainerLogAvgMemUsage(container.getId());
		double expectedAverage = ((log.getMemUsed() + log2.getMemUsed())/2d);
		assertEquals("Expected average to be " + expectedAverage + ", but was " + average + "!", expectedAverage, average, 0.1);
	}
	
	@Test
	public void testFindWithContainerIdAndPeriod() {
		Container container = TestUtil.newContainer();
		for(int i=0; i<25; i++) {
			ContainerLog log = TestUtil.newContainerLog();
			log.setTimestamp(new Timestamp(System.currentTimeMillis()));
			container.addLog(log);
		}
		
		for(int i=0; i<25; i++) {
			ContainerLog log = TestUtil.newContainerLog();
			log.setTimestamp(new Timestamp(System.currentTimeMillis() - 24 * 60 * 60 * 1001));
			container.addLog(log);
		}
		
		containerDao.store(container);
		
		List<ContainerLog> logs = containerDao.findWithContainerId(container.getId()).getLogs();
		assertEquals(50, logs.size());
		
		List<ContainerLog> results = containerLogDao.findWithContainerIdAndPeriod(container.getId(), 24);
		
		assertNotNull(results);
		assertEquals(25, results.size());
	}
}
