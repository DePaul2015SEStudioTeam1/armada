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
package edu.depaul.armada.controller;

import static org.junit.Assert.*;

import java.sql.Timestamp;
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
import edu.depaul.armada.model.ContainerMetric;
import edu.depaul.armada.util.TestUtil;

/**
 * Exercises log restful controller
 * 
 * @author ptrzyna
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beans/armada-config-test.xml"})
@TransactionConfiguration(transactionManager="armadaTransactionManager")
@Transactional
public class LogRestfulControllerTest {

	@Autowired private LogRestfulController logRestfulController;
	@Autowired private ContainerDao containerDao;

	@Test
	public void testGetLogsForPast24Hours() {
		List<ContainerMetric> results = logRestfulController.getLogsForPast24Hours(-1);
		assertNotNull(results);
		assertTrue(!results.isEmpty());
		for (int hour = 0; hour < 24; hour++) {
			ContainerMetric cm = results.get(hour);
			assertTrue(cm.getCpu() == 0);
			assertTrue(cm.getDisk() == 0);
			assertTrue(cm.getMem() == 0);
		}
		
		Container container = TestUtil.newContainer();
		for(int i=0; i<24; i++) {
			ContainerLog log = TestUtil.newContainerLog();
			log.setTimestamp(new Timestamp(System.currentTimeMillis()));
			container.addLog(log);
		}
		
		for(int i=0; i<24; i++) {
			ContainerLog log = TestUtil.newContainerLog();
			log.setTimestamp(new Timestamp(System.currentTimeMillis() - 24 * 60 * 60 * 1001));
			container.addLog(log);
		}
		
		containerDao.store(container);
		
		List<ContainerLog> logs = containerDao.findWithContainerId(container.getId()).getLogs();
		assertEquals(48, logs.size());
		
		results = logRestfulController.getLogsForPast24Hours(container.getId());
		
		assertNotNull("List of results was null!", results);
		assertEquals(24, results.size());
	}

}
