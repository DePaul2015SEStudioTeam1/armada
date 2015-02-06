/**
 * 
 */
package edu.depaul.armada.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
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
@TransactionConfiguration(transactionManager="operationsTransactionManager")
@Transactional
public class ContainerLogDaoTest {

	//@Autowired private ContainerDao<Container> _dao;
	@Autowired private ContainerLogDao<ContainerLog> _logDao;
	
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
	public void testContainerLogAvgMemUsage(){
		
		String containerIdOne = "container 1";
		ContainerLog containerLogOne = new ContainerLog();
		ContainerLog containerLogTwo = new ContainerLog();
		ContainerLog containerLogThree = new ContainerLog();
		containerLogOne.setContainerId(containerIdOne);
		containerLogTwo.setContainerId(containerIdOne);
		containerLogThree.setContainerId(containerIdOne);
		long logOneMemUsage = 1;
		long logTwoMemUsage = 2;
		long logThreeMemUsage = 3;
		containerLogOne.setMemUsage(logOneMemUsage);
		containerLogTwo.setMemUsage(logTwoMemUsage);
		containerLogThree.setMemUsage(logThreeMemUsage);
		
		String containerIdTwo = "container 2";
		ContainerLog containerLogFour = new ContainerLog();
		ContainerLog containerLogFive = new ContainerLog();
		ContainerLog containerLogSix = new ContainerLog();
		containerLogFour.setContainerId(containerIdTwo);
		containerLogFive.setContainerId(containerIdTwo);
		containerLogSix.setContainerId(containerIdTwo);
		long logFourMemUsage = -1;
		long logFiveMemUsage = -2;
		long logSixMemUsage = -3;
		containerLogFour.setMemUsage(logFourMemUsage);
		containerLogFive.setMemUsage(logFiveMemUsage);
		containerLogSix.setMemUsage(logSixMemUsage);
		
		String containerIdThree = "container 3";
		ContainerLog containerLogSeven = new ContainerLog();
		containerLogSeven.setContainerId(containerIdThree);
		long logSevenMemUsage = 0;
		containerLogSeven.setMemUsage(logSevenMemUsage);
			
		String containerIdFour = "container 4";
		ContainerLog containerLogEight = new ContainerLog();
		containerLogEight.setContainerId(containerIdFour);
		long logEightMemUsage = 100;
		containerLogEight.setMemUsage(logEightMemUsage);
		
		assertEquals(2, _logDao.getContainerLogAvgMemUsage("container 1"));
		assertEquals(-2, _logDao.getContainerLogAvgMemUsage("container 2"));
		assertEquals(0, _logDao.getContainerLogAvgMemUsage("container 3"));
		assertEquals(33, _logDao.getContainerLogAvgMemUsage("container 4"));
	}

}
