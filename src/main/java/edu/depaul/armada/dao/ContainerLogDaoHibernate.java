/**
 *
 */
package edu.depaul.armada.dao;

import java.util.List;

import edu.depaul.armada.domain.ContainerLog;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.depaul.armada.domain.Container;

/**
 * @author ptrzyna
 *
 */
@Repository
public class ContainerLogDaoHibernate implements ContainerLogDao<ContainerLog> {

	private SessionFactory sessionFactory;

	@Autowired
	public ContainerLogDaoHibernate(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void store(ContainerLog containerLog) {
		sessionFactory.getCurrentSession().saveOrUpdate(containerLog);
		sessionFactory.getCurrentSession().flush();
	}

}
