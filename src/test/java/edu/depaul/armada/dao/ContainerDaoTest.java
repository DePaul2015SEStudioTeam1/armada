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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.depaul.armada.domain.Container;
import edu.depaul.armada.domain.ContainerLog;
import edu.depaul.armada.model.DashboardContainer;

/**
 * @author ptrzyna
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beans/armada-config-test.xml"})
@TransactionConfiguration(transactionManager="armadaTransactionManager")
@Transactional
public class ContainerDaoTest {

	@Autowired private ContainerDao dao;
	
	/**
	 * Test method for {@link edu.depaul.armada.dao.ContainerDao#store(java.lang.Object)}.
	 */
	@DirtiesContext
	@Test
	public void testStore() {
		
		try {
			dao.store(null);
			fail("Expected IllegalArgumentException!");
		}
		catch(IllegalArgumentException iae) {
			assertEquals("Container instance cannot be null!", iae.getMessage());
		}
		
		Container container = newContainer();
		dao.store(container);
		
		List<Container> containers = dao.getAll();
		
		assertEquals(1, containers.size());
	}

	/**
	 * Test method for {@link edu.depaul.armada.dao.ContainerDao#getAll()}.
	 */
	@DirtiesContext
	@Test
	public void testGetAll() {
		
		int expected = 10;
		
		for(int i=0; i<expected; i++) {
			Container container = newContainer();
			dao.store(container);
		}
		
		List<Container> containers = dao.getAll();
		
		assertEquals(expected, containers.size());
	}

	/**
	 * Test method for {@link edu.depaul.armada.dao.ContainerDao#get(long, int)}.
	 */
	@DirtiesContext
	@Test
	public void testGet() {
		Container container = newContainer();
		dao.store(container);
		
		List<Container> results = dao.get(0, 1);
		
		assertNotNull(results);
		assertEquals(1, results.size());
	}

	/**
	 * Test method for {@link edu.depaul.armada.dao.ContainerDao#findWithContainerId(java.lang.String)}.
	 */
	@DirtiesContext
	@Test
	public void testFindWithContainerId() {
		Container container = newContainer();
		container.setName("test1");
		dao.store(container);
		
		container = newContainer();
		container.setName("test2");
		dao.store(container);
		
		container = newContainer();
		container.setName("test3");
		dao.store(container);
		
		Container result = dao.findWithContainerId(1);
		assertNotNull(result);
		assertEquals(1, result.getId());
		
		result = dao.findWithContainerId(2);
		assertNotNull(result);
		assertEquals("test2", result.getName());
		
		result = dao.findWithContainerId(3);
		assertNotNull(result);
		assertEquals("test3", result.getName());
		
		result = dao.findWithContainerId(0);
		assertNull(result);
		
	}
	
	@Test
	public void testStoreWithChild() {
		Container container = newContainer();
		container.setContainerUniqueId("test");
		ContainerLog log = new ContainerLog();
		log.setTimestamp(new Timestamp(0));
		container.addLog(log);
		dao.store(container);
		
		assertTrue(container.getId() > 0);
		
		container = dao.findWithContainerUniqueId("test");
		
		assertNotNull(container);
		assertEquals(1, container.getLogs().size());
	}

	@Test
	public void testGetAllDashboardContainers() {
		for(int i=0; i<100; i++) {
			Container container = newContainer();
			container.addLog(newContainerLog());
			container.addLog(newContainerLog());
			container.addLog(newContainerLog());
			dao.store(container);
		}
		
		List<DashboardContainer> result = dao.getAllDashboardContainers();
		assertNotNull(result);
		assertEquals(100, result.size());
		
		// verify the ordering
	}
	
	@Test
	public void testGetDashboardContainers_long() {
		for(int i=0; i<100; i++) {
			Container container = newContainer();
			container.setName("Container " + i);
			container.addLog(newContainerLog());
			container.addLog(newContainerLog());
			container.addLog(newContainerLog());
			dao.store(container);
		}
		long id1 = 0;
		int count1 = 5;
		List<DashboardContainer> result1 = dao.getDashboardContainers(id1, count1);
		assertNotNull(result1);
		assertEquals(count1, result1.size());
		for (int j=0; j < count1; j++){
			assertEquals("Container " + j, result1.get(j).name);
		}
		
		long id2 = 5;
		int count2 = 3;
		List<DashboardContainer> result2 = dao.getDashboardContainers(id2, count2);
		assertNotNull(result2);
		assertEquals(count2, result2.size());
		for (int k=0; k < count2; k++){
			assertEquals("Container " + k, result2.get(k).name);
		}
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
		log.setTimestamp(new Timestamp(RandomUtils.nextLong(0, System.currentTimeMillis())));
		log.setCpuUsed(RandomUtils.nextLong(0, 100));
		log.setCpuTotal(100);
		log.setMemUsed(RandomUtils.nextLong(0, 100));
		log.setMemTotal(100);
		log.setDiskUsed(RandomUtils.nextLong(0, 100));
		log.setDiskTotal(100);
		return log;
	}
}
