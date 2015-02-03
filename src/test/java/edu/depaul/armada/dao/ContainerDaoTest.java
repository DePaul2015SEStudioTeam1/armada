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

/**
 * @author ptrzyna
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beans/armada-config-test.xml"})
@TransactionConfiguration(transactionManager="operationsTransactionManager")
@Transactional
public class ContainerDaoTest {

	@Autowired private ContainerDao<Container> _dao;
	
	/**
	 * Test method for {@link edu.depaul.armada.dao.ContainerDao#store(java.lang.Object)}.
	 */
	@DirtiesContext
	@Test
	public void testStore() {
		
		try {
			_dao.store(null);
			fail("Expected IllegalArgumentException!");
		}
		catch(IllegalArgumentException iae) {
			assertEquals("Container instance cannot be null!", iae.getMessage());
		}
		
		Container container = new Container();
		_dao.store(container);
		
		List<Container> containers = _dao.getAll();
		
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
			_dao.store(container);
		}
		
		List<Container> containers = _dao.getAll();
		
		assertEquals(expected, containers.size());
	}

	/**
	 * Test method for {@link edu.depaul.armada.dao.ContainerDao#get(long, int)}.
	 */
	@DirtiesContext
	@Test
	public void testGet() {
		Container container = new Container();
		_dao.store(container);
		
		List<Container> results = _dao.get(0, 1);
		
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
		container.setContainerId("test1");
		_dao.store(container);
		
		container = new Container();
		container.setContainerId("test2");
		_dao.store(container);
		
		container = new Container();
		container.setContainerId("test3");
		_dao.store(container);
		
		Container result = _dao.findWithContainerId("test1");
		assertNotNull(result);
		assertEquals("test1", result.getContainerId());
		
		result = _dao.findWithContainerId("test2");
		assertNotNull(result);
		assertEquals("test2", result.getContainerId());
		
		result = _dao.findWithContainerId("test3");
		assertNotNull(result);
		assertEquals("test3", result.getContainerId());
		
		result = _dao.findWithContainerId("test0");
		assertNull(result);
		
		try {
			_dao.findWithContainerId(null);
			fail("Expected IllegalArgumentException!");
		}
		catch(IllegalArgumentException iae) {
			assertEquals("Parameter 'containerId' cannot be null!", iae.getMessage());
		}
	}

}