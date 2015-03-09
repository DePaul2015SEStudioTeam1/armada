package edu.depaul.armada.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
