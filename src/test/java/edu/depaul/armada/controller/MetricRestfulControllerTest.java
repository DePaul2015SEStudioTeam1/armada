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

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.depaul.armada.dao.PreferenceDao;
import edu.depaul.armada.domain.Preference;
import edu.depaul.armada.model.Metric;
import edu.depaul.armada.model.ThresholdMetric;


/**
 * Exercises metric restful controller
 * 
 * @author rcraddol
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beans/armada-config-test.xml"})
@TransactionConfiguration(transactionManager="armadaTransactionManager")
@Transactional
public class MetricRestfulControllerTest {
	@Autowired private MetricRestfulController metricRestfulController;
	@Autowired private PreferenceDao preferenceDao;
	
	@Test
	public void testThresholds() {
		Preference memory = new Preference();
		memory.setName("memory_threshold");
		memory.setValue(85);
		preferenceDao.storePreference(memory);
		
		Preference disk = new Preference();
		disk.setName("disk_threshold");
		disk.setValue(85);
		preferenceDao.storePreference(disk);
		
		Preference cpu = new Preference();
		cpu.setName("cpu_threshold");
		cpu.setValue(85);
		preferenceDao.storePreference(cpu);

		Preference refresh = new Preference();
		refresh.setName("refresh_page");
		refresh.setValue(85);
		preferenceDao.storePreference(refresh);
		
		List<ThresholdMetric> results = metricRestfulController.getThresholdStats(0);
		assertNotNull(results);
		assertTrue(results.isEmpty());
		
		results = metricRestfulController.getThresholdStats(4);
		assertNotNull(results);
		
		ThresholdMetric threshold = results.get(0);
		assertNotNull(threshold);
	}

	@Test
	public void testGetContainerCounts() {
		List<Metric> results = metricRestfulController.getContainerCounts(0);
		assertNotNull(results);
		assertTrue(results.isEmpty());
		
		int counts = 4;
		results = metricRestfulController.getContainerCounts(counts);
		assertNotNull(results);
		assertTrue(results.size() == counts);
		
		Metric metric = results.get(0);
		assertNotNull(metric.getHour());
		assertNotNull(metric.getValue());
	}

}
