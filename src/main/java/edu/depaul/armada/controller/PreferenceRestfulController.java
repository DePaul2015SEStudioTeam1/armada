/*
 * The MIT License (MIT)
 * Copyright (c) <year> <copyright holders> 
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

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.depaul.armada.dao.PreferenceDao;
import edu.depaul.armada.domain.DashboardPreferenceList;
import edu.depaul.armada.domain.Preference;
import edu.depaul.armada.model.DashboardPreference;

/**
 * Uses several Spring features, most centrally the @RequestMapping annotation. This sends web 
 * requests to specific pieces of the code, and then sends back the results through @ResponseBody. 
 * It also uses the @RequestBody annotation to convert HTTP requests into parameters that can be 
 * handled by a Java method. This class uses these Spring components to either return all of the 
 * DashboardPreferences, or to set them.
 * @author johnplante
 *
 */
@Controller
@RequestMapping("/preferences")
public class PreferenceRestfulController {
	
	private final Logger log = Logger.getLogger(getClass());
	
	private PreferenceDao preferenceDao;
	
	/**
	 * Creates a reference to the PreferenceDao object that it is passed.
	 * @param dao
	 */
	@Autowired
	public void setPreferenceDao(PreferenceDao dao) {
		preferenceDao = dao;
	}
	
	/**
	 * Returns a List of DashboardPreference objects from the PreferenceDao.
	 * @return List<DashboardPreference>
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public List<DashboardPreference> getAll() {
		return preferenceDao.getAll();
	}
	
	/**
	 * Accepts a set of user preferences from the dashboard, via an HTTP request, and sets the 
	 * preferences accordingly.
	 * @param preferences
	 */
	@RequestMapping(value = "/setAll", method = RequestMethod.POST)
	public void setAll( @RequestBody DashboardPreferenceList preferences) {
		for(DashboardPreference temp : preferences) {
			Preference pref = preferenceDao.findWithPreferenceName(temp.name);
			if(pref == null) {
				pref = new Preference();
				pref.setName(temp.name);
			}
			pref.setValue(temp.value);
			try {
				preferenceDao.storePreference(pref);
			}
			catch(Exception e) {
				log.error("Skipping adding a preference with name: " + temp.name + " due to an error!", e);
				continue;
			}
		}
	}	
}
