/**
 * 
 */
package edu.depaul.armada.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

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
	@DirtiesContext
	@Test
	public void testStore() {
		
		try {
			logDao.store(null);
			fail("Expected IllegalArgumentException!");
		}
		catch(IllegalArgumentException iae) {
			assertEquals("ContainerLog instance cannot be null!", iae.getMessage());
		}
		
		ContainerLog containerLog = new ContainerLog();
		ContainerLog containerLogTwo = new ContainerLog();
		ContainerLog containerLogThree = new ContainerLog();
		long containerId = 1;
		long containerIdTwo = 2;
		containerLog.setId(containerId);
		containerLogTwo.setId(containerId);
		containerLogThree.setId(containerIdTwo);
		
		logDao.store(containerLog);
		logDao.store(containerLogTwo);
		logDao.store(containerLogThree);
		
		List<ContainerLog> containerLogs = logDao.findWithContainerId(containerId);
		
		assertEquals(2, containerLogs.size());
	}
	
	/**
	 * Test method for {@link edu.depaul.armada.dao.ContainerLogDao#findWithContainerId(java.lang.String)}.
	 */
	@DirtiesContext
	@Test
	public void testFindWithContainerId() {
		
		ContainerLog containerLog = new ContainerLog();
		ContainerLog containerLogTwo = new ContainerLog();
		ContainerLog containerLogThree = new ContainerLog();
		long containerId = 1;
		long containerIdTwo = 2;
		containerLog.setId(containerId);
		containerLogTwo.setId(containerId);
		containerLogThree.setId(containerIdTwo);
		
		logDao.store(containerLog);
		logDao.store(containerLogTwo);
		logDao.store(containerLogThree);
		
		List<ContainerLog> containerLogs = logDao.findWithContainerId(containerId);
		
		assertEquals(2, containerLogs.size());
	}
	
	/**
	 * Test method for {@link edu.depaul.armada.dao.ContainerLogDao#getContainerLogAvgMemUsage(java.lang.String)}.
	 * Also test method for {@link edu.depaul.armada.dao.ContainerLogDao#getContainerLogAvgCpuUsage(java.lang.String)}.
	 * Also test method for {@link edu.depaul.armada.dao.ContainerLogDao#getContainerLogAvgFileSystemUsage(java.lang.String)}.
	 */
	@DirtiesContext
	@Test
	public void testFindWithContainerIdCheckResult() {
		
		ContainerLog containerLog = new ContainerLog();
		ContainerLog containerLogTwo = new ContainerLog();
		ContainerLog containerLogThree = new ContainerLog();
		long containerId = 1;
		long containerIdTwo = 2;
		containerLog.setId(containerId);
		containerLogTwo.setId(containerId);
		containerLogThree.setId(containerIdTwo);
		
		logDao.store(containerLog);
		logDao.store(containerLogTwo);
		logDao.store(containerLogThree);
		
		List<ContainerLog> containerLogs = logDao.findWithContainerId(containerId);
		
		assertEquals(containerId, containerLogs.get(1).getId());
	}
	
	/**
	 * Test method for {@link edu.depaul.armada.dao.ContainerLogDao#getContainerLogAvgMemUsage(java.lang.String)}.
	 */
	@DirtiesContext
	@Test
	public void testContainerLogAvgUsageMethods(){
		
		long containerIdOne = 1;
		ContainerLog containerLogOne = new ContainerLog();
		ContainerLog containerLogTwo = new ContainerLog();
		ContainerLog containerLogThree = new ContainerLog();
		containerLogOne.setId(containerIdOne);
		containerLogTwo.setId(containerIdOne);
		containerLogThree.setId(containerIdOne);
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
		
		long containerIdTwo = 2;
		ContainerLog containerLogFour = new ContainerLog();
		ContainerLog containerLogFive = new ContainerLog();
		ContainerLog containerLogSix = new ContainerLog();
		containerLogFour.setId(containerIdTwo);
		containerLogFive.setId(containerIdTwo);
		containerLogSix.setId(containerIdTwo);
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
		
		long containerIdThree = 3;
		ContainerLog containerLogSeven = new ContainerLog();
		containerLogSeven.setId(containerIdThree);
		long usageSeven = 0;
		containerLogSeven.setMemUsed(usageSeven);
		containerLogSeven.setCpuUsed(usageSeven);
		containerLogSeven.setDiskUsed(usageSeven);
		logDao.store(containerLogSeven);
			
		long containerIdFour = 4;
		ContainerLog containerLogEight = new ContainerLog();
		ContainerLog containerLogNine = new ContainerLog();
		ContainerLog containerLogTen = new ContainerLog();
		containerLogEight.setId(containerIdFour);
		containerLogNine.setId(containerIdFour);
		containerLogTen.setId(containerIdFour);
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
		
		assertEquals(2, logDao.getContainerLogAvgMemUsage(1));
		assertEquals(-2, logDao.getContainerLogAvgMemUsage(2));
		assertEquals(0, logDao.getContainerLogAvgMemUsage(3));
		assertEquals(33, logDao.getContainerLogAvgMemUsage(4));
		
		assertEquals(2, logDao.getContainerLogAvgCpuUsage(1));
		assertEquals(-2, logDao.getContainerLogAvgCpuUsage(2));
		assertEquals(0, logDao.getContainerLogAvgCpuUsage(3));
		assertEquals(33, logDao.getContainerLogAvgCpuUsage(4));
		
		assertEquals(2, logDao.getContainerLogAvgFileSystemUsage(1));
		assertEquals(-2, logDao.getContainerLogAvgFileSystemUsage(2));
		assertEquals(0, logDao.getContainerLogAvgFileSystemUsage(3));
		assertEquals(33, logDao.getContainerLogAvgFileSystemUsage(4));
	}

}
