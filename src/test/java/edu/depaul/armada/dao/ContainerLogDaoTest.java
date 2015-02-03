/**
 * 
 */
package edu.depaul.armada.dao;

import static org.junit.Assert.assertEquals;
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
			assertEquals("Container instance cannot be null!", iae.getMessage());
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
	 * Test method for {@link edu.depaul.armada.dao.ContainerLogDao#getAll()}.
	 */
	@DirtiesContext
	@Test
	public void testFindWithContainerId() {
		Container container = new Container();
		String containerId = container.getContainerId();
		ContainerLog containerLog = new ContainerLog();
		
		_logDao.store(containerLog);	
		
		List<ContainerLog> containerLogs = _logDao.findWithContainerId(containerId);
		
		assertEquals(1, containerLogs.size());
	}
	
	

}
