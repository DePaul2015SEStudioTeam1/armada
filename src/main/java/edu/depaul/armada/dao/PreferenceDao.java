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
	 * Returns a List<DashboardPreference> of all of the preference data in the database.
	 * @return List<DashboardPreference>
	 */
	List<DashboardPreference> getAll();
	
	/**
	 * Accepts a Preference object, and uses Hibernate's saveOrUpdate method to save the
	 * most recent preference data in the database.
	 * @param preference Preference
	 */
	void storePreference(Preference preference);

	/**
	 * Returns a single Preference object with a name field matching the one specified
	 * as a parameter.
	 * @param name String
	 * @return Preference
	 */
	Preference findWithPreferenceName(String name);
}
