package edu.depaul.armada.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.depaul.armada.domain.Container;
import edu.depaul.armada.domain.Preference;
import edu.depaul.armada.model.DashboardPreference;
import edu.depaul.armada.util.TestUtil;

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
	
	/**
	 * 
	 */
	@DirtiesContext
	@Test
	public void testStorePreference() {
		
		try {
			dao.storePreference(null);
			fail("Expected IllegalArgumentException!");
		}
		catch(IllegalArgumentException iae) {
			assertEquals("Container instance cannot be null!", iae.getMessage());
		}
		
		Preference preference = newPreference();
		dao.storePreference(preference);
		
		List<DashboardPreference> preferences = dao.getAll();
		
		assertEquals(1, preferences.size());
	}

	/**
	 * Test method for {@link edu.depaul.armada.dao.ContainerDao#getAll()}.
	 */
	@DirtiesContext
	@Test
	public void testGetAll() {
		
		int expected = 3;
		
		for(int i=0; i<expected; i++) {
			Preference preference = newPreference();
			dao.storePreference(preference);
		}
		
		List<DashboardPreference> preferences = dao.getAll();
		
		assertEquals(expected, preferences.size());
	}
	
	private Preference newPreference() {
		Preference preference = new Preference();
		preference.setKey("cpu");
		preference.setValue(90);
		return preference;
	}
}
