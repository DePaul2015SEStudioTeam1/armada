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

import edu.depaul.armada.domain.Container;
import edu.depaul.armada.domain.ContainerLog;

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
		
		Container container = new Container();
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
			Container container = new Container();
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
		Container container = new Container();
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
		Container container = new Container();
		container.setName("test1");
		dao.store(container);
		
		container = new Container();
		container.setName("test2");
		dao.store(container);
		
		container = new Container();
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
		Container container = new Container();
		container.setContainerUniqueId("test");
		container.addLog(new ContainerLog());
		dao.store(container);
		
		assertTrue(container.getId() > 0);
		
		container = dao.findWithContainerUniqueId("test");
		
		assertNotNull(container);
		assertEquals(1, container.getLogs().size());
	}

}
