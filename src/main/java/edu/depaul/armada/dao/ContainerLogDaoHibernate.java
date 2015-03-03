/**
 *
 */
package edu.depaul.armada.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.depaul.armada.domain.ContainerLog;
import edu.depaul.armada.model.Metric;
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

	/* (non-Javadoc)
	 * @see edu.depaul.armada.dao.ContainerLogDao#findWithContainerIdAndPeriod(long, int)
	 */
	@Override
	public List<Metric> findWithContainerIdAndPeriod(long containerId, int periodCount, String fieldToAverage) {
		List<Metric> metrics = new ArrayList<Metric>(periodCount);
		Calendar cal = Calendar.getInstance();
		for(int i=0; i<periodCount; i++) {
			Date end = cal.getTime();
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			cal.add(Calendar.HOUR_OF_DAY, -1);
			Date start = cal.getTime();
			
			Criteria criteria = newCriteria();
			criteria.createAlias("container", "container");
			criteria.add(Restrictions.eq("container.id", containerId));
			criteria.add(Restrictions.le("timestamp", end));
			criteria.add(Restrictions.gt("timestamp", start));	// we don't want overlap here
			criteria.setProjection(Projections.avg(fieldToAverage));
			Object result = criteria.uniqueResult();
			int count = (result == null)? 0 : ((Double) result).intValue();
			Metric temp = new Metric();
			temp.setHour(hour);
			temp.setValue(count);
			metrics.add(temp);
		}
		Collections.reverse(metrics);	// we want the current time to be the last hour
		return metrics;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.dao.ContainerLogDao#getStateCounts(int, int)
	 */
	@Override
	public List<Metric> getStateCounts(long memThreshold, long cpuThreshold, long diskThreshold, int periodCountInHours) {
		List<Metric> metrics = new ArrayList<Metric>(periodCountInHours);
		Calendar cal = Calendar.getInstance();
		for(int i=0; i<periodCountInHours; i++) {
			Date end = cal.getTime();
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			cal.add(Calendar.HOUR_OF_DAY, -1);
			Date start = cal.getTime();
			
			Criteria criteria = newCriteria();
			criteria.add(Restrictions.le("timestamp", end));
			criteria.add(Restrictions.gt("timestamp", start));	// we don't want overlap here
			criteria.add(Restrictions.disjunction()
					.add(Restrictions.ge("cpuUsed", cpuThreshold))
					.add(Restrictions.ge("memUsed", memThreshold))
					.add(Restrictions.ge("diskUsed", diskThreshold))
			);
			criteria.setProjection(Projections.countDistinct("container"));
			int count = ((Long) criteria.uniqueResult()).intValue();
			Metric temp = new Metric();
			temp.setHour(hour);
			temp.setValue(count);
			metrics.add(temp);
		}
		Collections.reverse(metrics);	// we want the current time to be the last hour
		return metrics;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.dao.ContainerLogDao#getContainerCounts(int)
	 */
	@Override
	public List<Metric> getContainerCounts(int periodInHours) {
		List<Metric> metrics = new ArrayList<Metric>(periodInHours);
		Calendar cal = Calendar.getInstance();
		for(int i=0; i<periodInHours; i++) {
			Date end = cal.getTime();
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			cal.add(Calendar.HOUR_OF_DAY, -1);
			Date start = cal.getTime();
			
			Criteria criteria = newCriteria();
			criteria.add(Restrictions.le("timestamp", end));
			criteria.add(Restrictions.gt("timestamp", start));	// we don't want overlap here
			criteria.setProjection(Projections.countDistinct("container"));
			int count = ((Long) criteria.uniqueResult()).intValue();
			Metric temp = new Metric();
			temp.setHour(hour);
			temp.setValue(count);
			metrics.add(temp);
		}
		Collections.reverse(metrics);	// we want the current time to be the last hour
		return metrics;
	}
	
}
