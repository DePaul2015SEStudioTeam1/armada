/**
 * 
 */
package edu.depaul.armada.dao;

import java.util.List;

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
public class ContainerDaoHibernate implements ContainerDao<Container> {

	private SessionFactory sessionFactory;
	
	@Autowired
	public ContainerDaoHibernate(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.operations.dao.ContainerDao#store(edu.depaul.armada.operations.domain.Container)
	 */
	@Override
	public void store(Container container) {
		sessionFactory.getCurrentSession().saveOrUpdate(container);
		sessionFactory.getCurrentSession().flush();
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.operations.dao.ContainerDao#getAllContainers()
	 */
	@Override
	public List<Container> getAll() {
		Query query = sessionFactory.getCurrentSession().createQuery("from Container");
		return query.list();
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.operations.dao.ContainerDao#get(long, int)
	 */
	@Override
	public List<Container> get(long id, int count) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Container where id > :id");
		query.setLong("id", id);
		query.setMaxResults(count);
		return query.list();
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.operations.dao.ContainerDao#findWithAgentId(java.lang.String)
	 */
	@Override
	public Container findWithAgentId(String agentId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Container where agentId = :agentId");
		query.setString("agentId", agentId);
		return (Container) query.uniqueResult();
	}
	
	
	
}
