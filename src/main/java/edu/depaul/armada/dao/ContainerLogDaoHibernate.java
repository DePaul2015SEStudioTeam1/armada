/**
 *
 */
package edu.depaul.armada.dao;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.depaul.armada.domain.ContainerLog;
import edu.depaul.armada.util.AssertUtil;

/**
 * Implementation of the ContainerLogDao
 * 
 * @author ptrzyna and jplante
 */
@Transactional
@Repository
public class ContainerLogDaoHibernate implements ContainerLogDao {

	private SessionFactory sessionFactory;
	
	public ContainerLogDaoHibernate() {}

	@Autowired
	public ContainerLogDaoHibernate(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<ContainerLog> findWithContainerId(long containerId) {
		AssertUtil.assertNotNull(containerId, "Parameter 'containerId' cannot be null!");
		Criteria criteria = newCriteria();
		criteria.createAlias("container", "container");
		criteria.add(Restrictions.eq("container.id", containerId));
		List<ContainerLog> logs = criteria.list();
		return (logs == null)? Collections.<ContainerLog>emptyList() : logs;
	}
	
	@Override
	public double getContainerLogAvgMemUsage(long containerId) {
		return getAverage(containerId, "memUsed");
	}

	@Override
	public double getContainerLogAvgCpuUsage(long containerId) {
		return getAverage(containerId, "cpuUsed");
	}

	@Override
	public double getContainerLogAvgDiskUsage(long containerId) {
		return getAverage(containerId, "filesystemUsed");
	}
	
	private double getAverage(long containerId, String property) {
		Criteria criteria = newCriteria();
		criteria.createAlias("container", "container");
		criteria.add(Restrictions.eq("container.id", containerId));
		criteria.setProjection(Projections.avg(property));
		Double result = (Double) criteria.uniqueResult();
		return (result == null)? 0.0 : result;
	}
	
	/**
	 * Return new criteria
	 * 
	 * @return new criteria
	 */
	private Criteria newCriteria() {
		return sessionFactory.getCurrentSession().createCriteria(ContainerLog.class);
	}
	
}
