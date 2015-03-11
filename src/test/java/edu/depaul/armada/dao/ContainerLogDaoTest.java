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

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.depaul.armada.domain.Container;
import edu.depaul.armada.domain.ContainerLog;
import edu.depaul.armada.util.TestUtil;

/**
 * @author jplante
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beans/armada-config-test.xml"})
@TransactionConfiguration(transactionManager="armadaTransactionManager")
@Transactional
public class ContainerLogDaoTest {
 
	@Autowired private ContainerDao containerDao;
	@Autowired private ContainerLogDao containerLogDao;
	
	@Test
	public void testFindWithContainerId() {
		
		Container container = TestUtil.newContainer();
		ContainerLog[] logs = new ContainerLog[3];
		for(int i=0; i<logs.length; i++) {
			logs[i] = TestUtil.newContainerLog();
			container.addLog(logs[i]);
		}
		
		containerDao.store(container);
		
		List<ContainerLog> result = containerLogDao.findWithContainerId(container.getId());
		
		assertEquals(logs.length, result.size());
		assertEquals(logs[0].getId(), result.get(0).getId());
	}
	
	@Test
	public void testGetContainerLogAvgMemUsage_no_matching_container_log() {
		double average = containerLogDao.getContainerLogAvgMemUsage(-1);
		assertEquals("Expected average to be 0, but was " + average + "!", 0, average, 0.1);
	}
	
	@Test
	public void testGetContainerLogAvgMemUsage_matching_container_logs() {
		
		Container container = TestUtil.newContainer();
		ContainerLog log = TestUtil.newContainerLog();
		container.addLog(log);
	
		containerDao.store(container);	
		double average = containerLogDao.getContainerLogAvgMemUsage(container.getId());
		
		assertEquals("Expected average to be " + log.getMemUsed() + ", but was " + average + "!", log.getMemUsed(), average, 0.1);
		
		container = containerDao.findWithContainerId(container.getId());
		ContainerLog log2 = TestUtil.newContainerLog();
		container.addLog(log2);
		containerDao.store(container);
		
		List<ContainerLog> logs = containerLogDao.findWithContainerId(container.getId());
		assertEquals(2, logs.size());
		
		average = containerLogDao.getContainerLogAvgMemUsage(container.getId());
		double expectedAverage = ((log.getMemUsed() + log2.getMemUsed())/2d);
		assertEquals("Expected average to be " + expectedAverage + ", but was " + average + "!", expectedAverage, average, 0.1);
	}
}
