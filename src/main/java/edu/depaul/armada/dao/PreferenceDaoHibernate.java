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
	@Override
	public void storePreference(Preference preference) {
		AssertUtil.assertNotNull(preference, "preference instance cannot be null!");
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(preference);
	}
	
	/*
	 * (non-Javadoc)
	 * @see edu.depaul.armada.dao.PreferenceDao#findWithPreferenceKey(java.lang.String)
	 */
	@Override
	public Preference findWithPreferenceKey(String key) {
		AssertUtil.assertNotNull(key, "Parameter 'key' cannot be null!");
		Query query = sessionFactory.getCurrentSession().createQuery("from Preference where key = :key");
		query.setString("key", key);
		return (Preference) query.uniqueResult();
	}

}
