/**
 * 
 */
package edu.depaul.armada.controller;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.depaul.armada.dao.ContainerDao;
import edu.depaul.armada.domain.Container;
import edu.depaul.armada.domain.ContainerLog;
import edu.depaul.armada.model.DashboardContainer;
import edu.depaul.armada.util.TestUtil;

/**
 * Exercises the ContainerRestfulController
 * 
 * @author ptrzyna
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beans/armada-config-test.xml"})
@TransactionConfiguration(transactionManager="armadaTransactionManager")
@Transactional
public class ContainerRestfulControllerTest {

	@Autowired private ContainerDao containerDao;
	@Autowired private ContainerRestfulController containerRestfulController;
	
	/**
	 * Test method for {@link edu.depaul.armada.controller.ContainerRestfulController#getAll()}.
	 */
	@Test
	public void testGetAllDashboardContainers() {
		List<DashboardContainer> result = containerRestfulController.getAllDashboardContainers();
		assertTrue(result.isEmpty());
		
		Container container = TestUtil.newContainer();
		ContainerLog[] logs = new ContainerLog[3];
		for(int i=0; i<logs.length; i++) {
			logs[i] = TestUtil.newContainerLog();
			container.addLog(logs[i]);
		}
		
		containerDao.store(container);
		
		Container container2 = TestUtil.newContainer();
		ContainerLog[] logs2 = new ContainerLog[3];
		for(int i=0; i<logs2.length; i++) {
			logs2[i] = TestUtil.newContainerLog();
			container2.addLog(logs2[i]);
		}
		
		containerDao.store(container2);
		
		result = containerRestfulController.getAllDashboardContainers();
		// container corresponds to dashboard container
		assertEquals("Expected 2 because there are only two containers.", 2, result.size());
		
		// verify that DashboardContainer instances are populated correctly
		// we want to ensure that each container is reporting latest information
		assertEqualsBetweenContainers(container, logs[2], result.get(0));
		assertEqualsBetweenContainers(container2, logs2[2], result.get(1));
	}
	
	private void assertEqualsBetweenContainers(Container container, ContainerLog latestLog, DashboardContainer dashboardContainer) {
		assertEquals(container.getCAdvisorURL(), dashboardContainer.cAdvisorURL);
		assertEquals(container.getContainerUniqueId(), dashboardContainer.containerUniqueId);
		assertEquals(container.getName(), dashboardContainer.name);
		
		assertEquals(latestLog.getCpuUsed(), dashboardContainer.cpuUsed);
		assertEquals(latestLog.getCpuTotal(), dashboardContainer.cpuTotal);
		assertEquals(latestLog.getMemUsed(), dashboardContainer.memUsed);
		assertEquals(latestLog.getMemTotal(), dashboardContainer.memTotal);
		assertEquals(latestLog.getDiskUsed(), dashboardContainer.diskUsed);
		assertEquals(latestLog.getDiskTotal(), dashboardContainer.diskTotal);
	}

	/**
	 * Test method for {@link edu.depaul.armada.controller.ContainerRestfulController#getPage(long)}.
	 */
	@Test
	public void testGetPageOfDashboardContainers() {
		
		Container[] containers = new Container[25];
		ContainerLog[] logs = new ContainerLog[25];
		for(int i=0; i<25; i++) {
			containers[i] = TestUtil.newContainer();
			logs[i] = TestUtil.newContainerLog();
			containers[i].addLog(logs[i]);
			containerDao.store(containers[i]);
		}
		
		List<DashboardContainer> result = containerRestfulController.getPageOfDashboardContainers(0);
		assertEquals(10, result.size());
		
		for(int i=0; i<result.size(); i++) {
			assertEqualsBetweenContainers(containers[i], logs[i], result.get(i));
		}
	}

}
