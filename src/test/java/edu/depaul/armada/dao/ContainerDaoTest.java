/**
 * 
 */
package edu.depaul.armada.dao;

import static org.junit.Assert.*;

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
		Container container = new Container();
		_dao.store(container);
		
		List<Container> containers = _dao.getAll();
		
		assertEquals(1, containers.size());
	}

	/**
	 * Test method for {@link edu.depaul.armada.dao.ContainerDao#get(long, int)}.
	 */
	@Test
	@Ignore
	public void testGet() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.depaul.armada.dao.ContainerDao#findWithDockerId(java.lang.String)}.
	 */
	@Test
	@Ignore
	public void testFindWithDockerId() {
		fail("Not yet implemented");
	}

}
