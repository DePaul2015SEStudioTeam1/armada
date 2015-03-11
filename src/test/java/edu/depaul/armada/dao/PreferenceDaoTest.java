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
