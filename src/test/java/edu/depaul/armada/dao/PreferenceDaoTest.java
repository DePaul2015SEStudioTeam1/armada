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

import edu.depaul.armada.domain.Preference;
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
public class PreferenceDaoTest {

	@Autowired private PreferenceDao dao;
	
	@Test
	public void testStorePreference() {
		
		try {
			dao.storePreference(null);
			fail("Expected IllegalArgumentException!");
		}
		catch(IllegalArgumentException iae) {
			assertEquals("preference instance cannot be null!", iae.getMessage());
		}
		
		Preference preference = newPreference("cpu");
		dao.storePreference(preference);
		
		List<DashboardPreference> preferences = dao.getAll();
		
		assertEquals(1, preferences.size());
	}

	@Test
	public void testGetAll() {
		
		int expected = 3;
		
		String[] names = new String[] {"cpu", "mem", "disk"};
		
		for(int i=0; i<expected; i++) {
			Preference preference = newPreference(names[i]);
			dao.storePreference(preference);
		}
		
		List<DashboardPreference> preferences = dao.getAll();
		
		assertEquals(expected, preferences.size());
	}
	
	private Preference newPreference(String name) {
		Preference preference = new Preference();
		preference.setName(name);
		preference.setValue(90);
		return preference;
	}
}
