/*
 * The MIT License (MIT)
 * 
 * Copyright (c) <year> <copyright holders> 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package edu.depaul.armada.dao;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.List;

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
import edu.depaul.armada.util.TestUtil;

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
		
		Container container = TestUtil.newContainer();
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
			Container container = TestUtil.newContainer();
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
		Container container = TestUtil.newContainer();
		dao.store(container);
		
		List<Container> results = dao.get(0, 1);
		
		assertNotNull(results);
		assertEquals(1, results.size());
	}

	@Test
	public void testFindWithContainerId() {
		
		Container[] containers = new Container[3];
		
		containers[0] = TestUtil.newContainer();
		containers[0].setName("test1");
		dao.store(containers[0]);
		
		containers[1] = TestUtil.newContainer();
		containers[1].setName("test2");
		dao.store(containers[1]);
		
		containers[2] = TestUtil.newContainer();
		containers[2].setName("test3");
		dao.store(containers[2]);
		
		Container result = dao.findWithContainerId(containers[0].getId());
		assertNotNull("Expected result to bo non null!", result);
		assertEquals(containers[0].getId(), result.getId());
		
		result = dao.findWithContainerId(containers[1].getId());
		assertNotNull(result);
		assertEquals("test2", result.getName());
		
		result = dao.findWithContainerId(containers[2].getId());
		assertNotNull(result);
		assertEquals("test3", result.getName());
		
		result = dao.findWithContainerId(-1);
		assertNull(result);
		
	}
	
	@Test
	public void testStoreWithChild() {
		Container container = TestUtil.newContainer();
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
			Container container = TestUtil.newContainer();
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
			Container container = TestUtil.newContainer();
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
		for (int k=5; k < count2; k++){
			assertEquals("Container " + k, result2.get(k).name);
		}
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
