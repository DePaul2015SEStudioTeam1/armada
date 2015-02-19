package edu.depaul.armada.dao;

import java.util.List;

import edu.depaul.armada.domain.Preference;
import edu.depaul.armada.model.DashboardPreference;


/**
 * Used for CRUD operations associated with DashboardPreference instances
 * 
 * @author Roland Craddolph
 */
public interface PreferenceDao {
	
	/**
	 * 
	 * @return
	 */
	List<DashboardPreference> getAll();
	
	/**
	 * 
	 * @param preference
	 */
	void storePreference(Preference preference);

	/**
	 * 
	 * @param key
	 * @return
	 */
	Preference findWithPreferenceKey(String key);
}
