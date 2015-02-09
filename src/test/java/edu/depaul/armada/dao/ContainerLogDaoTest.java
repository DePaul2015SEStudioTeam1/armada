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

	@Autowired private ContainerLogDao _logDao;
	
	/**
	 * Test method for {@link edu.depaul.armada.dao.ContainerLogDao#store(java.lang.Object)}.
	 */
	@DirtiesContext
	@Test
	public void testStore() {
		
		try {
			_logDao.store(null);
			fail("Expected IllegalArgumentException!");
		}
		catch(IllegalArgumentException iae) {
			assertEquals("ContainerLog instance cannot be null!", iae.getMessage());
		}
		
		ContainerLog containerLog = new ContainerLog();
		ContainerLog containerLogTwo = new ContainerLog();
		ContainerLog containerLogThree = new ContainerLog();
		String containerId = "test container ID one";
		String containerIdTwo = "test container ID two";
		containerLog.setContainerId(containerId);
		containerLogTwo.setContainerId(containerId);
		containerLogThree.setContainerId(containerIdTwo);
		
		_logDao.store(containerLog);
		_logDao.store(containerLogTwo);
		_logDao.store(containerLogThree);
		
		List<ContainerLog> containerLogs = _logDao.findWithContainerId(containerId);
		
		assertEquals(2, containerLogs.size());
	}
	
	/**
	 * Test method for {@link edu.depaul.armada.dao.ContainerLogDao#findWithContainerId(java.lang.String)}.
	 */
	@DirtiesContext
	@Test
	public void testFindWithContainerId() {
		
		try {
			_logDao.findWithContainerId(null);
			fail("Expected IllegalArgumentException!");
		}
		catch(IllegalArgumentException iae) {
			assertEquals("Parameter 'containerId' cannot be null!", iae.getMessage());
		}
		
		ContainerLog containerLog = new ContainerLog();
		ContainerLog containerLogTwo = new ContainerLog();
		ContainerLog containerLogThree = new ContainerLog();
		String containerId = "test container ID one";
		String containerIdTwo = "test container ID two";
		containerLog.setContainerId(containerId);
		containerLogTwo.setContainerId(containerId);
		containerLogThree.setContainerId(containerIdTwo);
		
		_logDao.store(containerLog);
		_logDao.store(containerLogTwo);
		_logDao.store(containerLogThree);
		
		List<ContainerLog> containerLogs = _logDao.findWithContainerId(containerId);
		
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
		String containerId = "test container ID one";
		String containerIdTwo = "test container ID two";
		containerLog.setContainerId(containerId);
		containerLogTwo.setContainerId(containerId);
		containerLogThree.setContainerId(containerIdTwo);
		
		_logDao.store(containerLog);
		_logDao.store(containerLogTwo);
		_logDao.store(containerLogThree);
		
		List<ContainerLog> containerLogs = _logDao.findWithContainerId(containerId);
		
		assertEquals(containerId, containerLogs.get(1).getContainerId());
	}
	
	/**
	 * Test method for {@link edu.depaul.armada.dao.ContainerLogDao#getContainerLogAvgMemUsage(java.lang.String)}.
	 */
	@DirtiesContext
	@Test
	public void testContainerLogAvgUsageMethods(){
		
		String containerIdOne = "container 1";
		ContainerLog containerLogOne = new ContainerLog();
		ContainerLog containerLogTwo = new ContainerLog();
		ContainerLog containerLogThree = new ContainerLog();
		containerLogOne.setContainerId(containerIdOne);
		containerLogTwo.setContainerId(containerIdOne);
		containerLogThree.setContainerId(containerIdOne);
		long usageOne = 1;
		long usageTwo = 2;
		long usageThree = 3;
		containerLogOne.setMemUsage(usageOne);
		containerLogTwo.setMemUsage(usageTwo);
		containerLogThree.setMemUsage(usageThree);
		containerLogOne.setTotalCpuUsage(usageOne);
		containerLogTwo.setTotalCpuUsage(usageTwo);
		containerLogThree.setTotalCpuUsage(usageThree);
		containerLogOne.setFilesystemUsage(usageOne);
		containerLogTwo.setFilesystemUsage(usageTwo);
		containerLogThree.setFilesystemUsage(usageThree);
		_logDao.store(containerLogOne);
		_logDao.store(containerLogTwo);
		_logDao.store(containerLogThree);
		
		String containerIdTwo = "container 2";
		ContainerLog containerLogFour = new ContainerLog();
		ContainerLog containerLogFive = new ContainerLog();
		ContainerLog containerLogSix = new ContainerLog();
		containerLogFour.setContainerId(containerIdTwo);
		containerLogFive.setContainerId(containerIdTwo);
		containerLogSix.setContainerId(containerIdTwo);
		long usageFour = -1;
		long usageFive = -2;
		long usageSix = -3;
		containerLogFour.setMemUsage(usageFour);
		containerLogFive.setMemUsage(usageFive);
		containerLogSix.setMemUsage(usageSix);
		containerLogFour.setTotalCpuUsage(usageFour);
		containerLogFive.setTotalCpuUsage(usageFive);
		containerLogSix.setTotalCpuUsage(usageSix);
		containerLogFour.setFilesystemUsage(usageFour);
		containerLogFive.setFilesystemUsage(usageFive);
		containerLogSix.setFilesystemUsage(usageSix);
		_logDao.store(containerLogFour);
		_logDao.store(containerLogFive);
		_logDao.store(containerLogSix);
		
		String containerIdThree = "container 3";
		ContainerLog containerLogSeven = new ContainerLog();
		containerLogSeven.setContainerId(containerIdThree);
		long usageSeven = 0;
		containerLogSeven.setMemUsage(usageSeven);
		containerLogSeven.setTotalCpuUsage(usageSeven);
		containerLogSeven.setFilesystemUsage(usageSeven);
		_logDao.store(containerLogSeven);
			
		String containerIdFour = "container 4";
		ContainerLog containerLogEight = new ContainerLog();
		ContainerLog containerLogNine = new ContainerLog();
		ContainerLog containerLogTen = new ContainerLog();
		containerLogEight.setContainerId(containerIdFour);
		containerLogNine.setContainerId(containerIdFour);
		containerLogTen.setContainerId(containerIdFour);
		long usageEight = 25;
		long usageNine = 25;
		long usageTen = 50;
		containerLogEight.setMemUsage(usageEight);
		containerLogNine.setMemUsage(usageNine);
		containerLogTen.setMemUsage(usageTen);
		containerLogEight.setTotalCpuUsage(usageEight);
		containerLogNine.setTotalCpuUsage(usageNine);
		containerLogTen.setTotalCpuUsage(usageTen);
		containerLogEight.setFilesystemUsage(usageEight);
		containerLogNine.setFilesystemUsage(usageNine);
		containerLogTen.setFilesystemUsage(usageTen);
		_logDao.store(containerLogEight);
		_logDao.store(containerLogNine);
		_logDao.store(containerLogTen);
		
		assertEquals(2, _logDao.getContainerLogAvgMemUsage("container 1"));
		assertEquals(-2, _logDao.getContainerLogAvgMemUsage("container 2"));
		assertEquals(0, _logDao.getContainerLogAvgMemUsage("container 3"));
		assertEquals(33, _logDao.getContainerLogAvgMemUsage("container 4"));
		
		assertEquals(2, _logDao.getContainerLogAvgCpuUsage("container 1"));
		assertEquals(-2, _logDao.getContainerLogAvgCpuUsage("container 2"));
		assertEquals(0, _logDao.getContainerLogAvgCpuUsage("container 3"));
		assertEquals(33, _logDao.getContainerLogAvgCpuUsage("container 4"));
		
		assertEquals(2, _logDao.getContainerLogAvgFileSystemUsage("container 1"));
		assertEquals(-2, _logDao.getContainerLogAvgFileSystemUsage("container 2"));
		assertEquals(0, _logDao.getContainerLogAvgFileSystemUsage("container 3"));
		assertEquals(33, _logDao.getContainerLogAvgFileSystemUsage("container 4"));
	}

}
