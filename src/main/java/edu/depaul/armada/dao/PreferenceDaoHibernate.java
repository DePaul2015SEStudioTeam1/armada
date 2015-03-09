/**
 * 
 */
package edu.depaul.armada.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.depaul.armada.domain.Container;
import edu.depaul.armada.domain.Preference;
import edu.depaul.armada.model.DashboardPreference;
import edu.depaul.armada.util.AssertUtil;

/**
 * An implementation of PreferenceDao. It uses the Hibernate framework 
 * to interact with the application’s database. It exists to provide 
 * a way for the application to retrieve preference data.
 * @author Roland T Craddolph
 *
 */
@Transactional
@Repository
public class PreferenceDaoHibernate implements PreferenceDao {
	private SessionFactory sessionFactory;
	
	public PreferenceDaoHibernate() {}
	
	@Autowired
	public PreferenceDaoHibernate(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.armada.dao.PreferenceDAO#getAllPreferences()
	 */
	/**
	 * Returns a List<DashboardPreference> of all of the preference data in the database.
	 * @return List<DashboardPreference>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DashboardPreference> getAll() {
		Query query = sessionFactory.getCurrentSession().createQuery("from Preference");
		return query.list();
	}
	
	/*
	 * (non-Javadoc)
	 * @see edu.depaul.armada.dao.PreferenceDao#storePreference(edu.depaul.armada.domain.Preference)
	 */
	/**
	 * Accepts a Preference object, and uses Hibernate's saveOrUpdate method to save the
	 * most recent preference data in the database.
	 * @param preference Preference
	 */
	@Override
	public void storePreference(Preference preference) {
		AssertUtil.assertNotNull(preference, "preference instance cannot be null!");
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(preference);
	}
	
	/*
	 * (non-Javadoc)
	 * @see edu.depaul.armada.dao.PreferenceDao#findWithPreferenceName(java.lang.String)
	 */
	/**
	 * Returns a single Preference object with a name field matching the one specified
	 * as a parameter.
	 * @param name String
	 * @return Preference
	 */
	@Override
	public Preference findWithPreferenceName(String name) {
		AssertUtil.assertNotNull(name, "Parameter 'name' cannot be null!");
		Query query = sessionFactory.getCurrentSession().createQuery("from Preference where name = :name");
		query.setString("name", name);
		return (Preference) query.uniqueResult();
	}

}
