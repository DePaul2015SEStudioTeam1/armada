package edu.depaul.armada.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.depaul.armada.domain.DashboardPreferenceList;
import edu.depaul.armada.model.DashboardPreference;

/**
 * 
 * @author Roland T Craddolph
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beans/armada-config-test.xml"})
@TransactionConfiguration(transactionManager="armadaTransactionManager")
@Transactional
@Ignore
public class PreferenceRestControllerTest {
	@Autowired private PreferenceRestfulController preferenceRestfulController;
	
	@Test
	public void testGetSetPreferences() {
		List<DashboardPreference> results = preferenceRestfulController.getAll();
		assertNotNull(results);
		assertTrue(results.size() == 4);
		
		DashboardPreferenceList prefList = new DashboardPreferenceList();
		DashboardPreference memory = new DashboardPreference();
		memory.name = "memory_threshold";
		memory.value = 85;
		prefList.add(memory);
		preferenceRestfulController.setAll(prefList);
		
		results = preferenceRestfulController.getAll();
		for (DashboardPreference dp : results) {
			if (dp.name.equalsIgnoreCase("memory_threshold")) {
				assertEquals(dp.value, 85);
			}
		}
	}

}
