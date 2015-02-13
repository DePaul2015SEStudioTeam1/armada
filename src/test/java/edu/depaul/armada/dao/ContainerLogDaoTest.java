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
 
	@Autowired private ContainerLogDao logDao;
	
	/**
	 * Test method for {@link edu.depaul.armada.dao.ContainerLogDao#store(java.lang.Object)}.
	 */
	@Test
	public void testStore() {
		
		try {
			logDao.store(null);
			fail("Expected IllegalArgumentException!");
		}
		catch(IllegalArgumentException iae) {
			assertEquals("ContainerLog instance cannot be null!", iae.getMessage());
		}
		
		ContainerLog containerLog = newContainerLog();
		ContainerLog containerLogTwo = newContainerLog();
		ContainerLog containerLogThree = newContainerLog();
		
		logDao.store(containerLog);
		logDao.store(containerLogTwo);
		logDao.store(containerLogThree);
		
		List<ContainerLog> containerLogs = logDao.findWithContainerId(containerLog.getId());
		
		assertEquals(1, containerLogs.size());
	}
	
	/**
	 * Test method for {@link edu.depaul.armada.dao.ContainerLogDao#findWithContainerId(java.lang.String)}.
	 */
	@Test
	public void testFindWithContainerId() {
		
		ContainerLog containerLog = newContainerLog();
		ContainerLog containerLogTwo = newContainerLog();
		ContainerLog containerLogThree = newContainerLog();
		
		logDao.store(containerLog);
		logDao.store(containerLogTwo);
		logDao.store(containerLogThree);
		
		List<ContainerLog> containerLogs = logDao.findWithContainerId(containerLog.getId());
		
		assertEquals(1, containerLogs.size());
	}
	
	/**
	 * Test method for {@link edu.depaul.armada.dao.ContainerLogDao#getContainerLogAvgMemUsage(java.lang.String)}.
	 * Also test method for {@link edu.depaul.armada.dao.ContainerLogDao#getContainerLogAvgCpuUsage(java.lang.String)}.
	 * Also test method for {@link edu.depaul.armada.dao.ContainerLogDao#getContainerLogAvgFileSystemUsage(java.lang.String)}.
	 */
	@Test
	public void testFindWithContainerIdCheckResult() {
		
		ContainerLog containerLog = newContainerLog();
		ContainerLog containerLogTwo = newContainerLog();
		ContainerLog containerLogThree = newContainerLog();
		
		logDao.store(containerLog);
		logDao.store(containerLogTwo);
		logDao.store(containerLogThree);
		
		long containerId = containerLog.getId();
		
		List<ContainerLog> containerLogs = logDao.findWithContainerId(containerId);
		
		assertEquals(containerId, containerLogs.get(0).getId());
	}
	
	/**
	 * Test method for {@link edu.depaul.armada.dao.ContainerLogDao#getContainerLogAvgMemUsage(java.lang.String)}.
	 */
	@Test
	public void testContainerLogAvgUsageMethods(){
		// TODO: break this up into separate methods
		// It will make it clearer what is being tested
		ContainerLog containerLogOne = newContainerLog();
		ContainerLog containerLogTwo = newContainerLog();
		ContainerLog containerLogThree = newContainerLog();
		long usageOne = 1;
		long usageTwo = 2;
		long usageThree = 3;
		containerLogOne.setMemUsed(usageOne);
		containerLogTwo.setMemUsed(usageTwo);
		containerLogThree.setMemUsed(usageThree);
		containerLogOne.setCpuUsed(usageOne);
		containerLogTwo.setCpuUsed(usageTwo);
		containerLogThree.setCpuUsed(usageThree);
		containerLogOne.setDiskUsed(usageOne);
		containerLogTwo.setDiskUsed(usageTwo);
		containerLogThree.setDiskUsed(usageThree);
		logDao.store(containerLogOne);
		logDao.store(containerLogTwo);
		logDao.store(containerLogThree);
		
		ContainerLog containerLogFour = newContainerLog();
		ContainerLog containerLogFive = newContainerLog();
		ContainerLog containerLogSix = newContainerLog();
		long usageFour = -1;
		long usageFive = -2;
		long usageSix = -3;
		containerLogFour.setMemUsed(usageFour);
		containerLogFive.setMemUsed(usageFive);
		containerLogSix.setMemUsed(usageSix);
		containerLogFour.setCpuUsed(usageFour);
		containerLogFive.setCpuUsed(usageFive);
		containerLogSix.setCpuUsed(usageSix);
		containerLogFour.setDiskUsed(usageFour);
		containerLogFive.setDiskUsed(usageFive);
		containerLogSix.setDiskUsed(usageSix);
		logDao.store(containerLogFour);
		logDao.store(containerLogFive);
		logDao.store(containerLogSix);
		
		ContainerLog containerLogSeven = newContainerLog();
		long usageSeven = 0;
		containerLogSeven.setMemUsed(usageSeven);
		containerLogSeven.setCpuUsed(usageSeven);
		containerLogSeven.setDiskUsed(usageSeven);
		logDao.store(containerLogSeven);
			
		ContainerLog containerLogEight = newContainerLog();
		ContainerLog containerLogNine = newContainerLog();
		ContainerLog containerLogTen = newContainerLog();
		long usageEight = 25;
		long usageNine = 25;
		long usageTen = 50;
		containerLogEight.setMemUsed(usageEight);
		containerLogNine.setMemUsed(usageNine);
		containerLogTen.setMemUsed(usageTen);
		containerLogEight.setCpuUsed(usageEight);
		containerLogNine.setCpuUsed(usageNine);
		containerLogTen.setCpuUsed(usageTen);
		containerLogEight.setDiskUsed(usageEight);
		containerLogNine.setDiskUsed(usageNine);
		containerLogTen.setDiskUsed(usageTen);
		logDao.store(containerLogEight);
		logDao.store(containerLogNine);
		logDao.store(containerLogTen);
		
		assertEquals(1, logDao.getContainerLogAvgMemUsage(containerLogOne.getId()));
		assertEquals(2, logDao.getContainerLogAvgMemUsage(containerLogTwo.getId()));
		assertEquals(3, logDao.getContainerLogAvgMemUsage(containerLogThree.getId()));
		assertEquals(-1, logDao.getContainerLogAvgMemUsage(containerLogFour.getId()));
		
		assertEquals(1, logDao.getContainerLogAvgCpuUsage(containerLogOne.getId()));
		assertEquals(2, logDao.getContainerLogAvgCpuUsage(containerLogTwo.getId()));
		assertEquals(3, logDao.getContainerLogAvgCpuUsage(containerLogThree.getId()));
		assertEquals(-1, logDao.getContainerLogAvgCpuUsage(containerLogFour.getId()));
		
		assertEquals(1, logDao.getContainerLogAvgFileSystemUsage(containerLogOne.getId()));
		assertEquals(2, logDao.getContainerLogAvgFileSystemUsage(containerLogTwo.getId()));
		assertEquals(3, logDao.getContainerLogAvgFileSystemUsage(containerLogThree.getId()));
		assertEquals(-1, logDao.getContainerLogAvgFileSystemUsage(containerLogFour.getId()));
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
}
