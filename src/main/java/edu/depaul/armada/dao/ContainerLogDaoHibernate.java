/**
 *
 */
package edu.depaul.armada.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
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
 * Implementation of the ContainerLogDao. A class that implements 
 * the ContainerLogDao interface. It uses the Hibernate framework 
 * to interact with the application’s database. It exists to provide 
 * a way for the application to retrieve Container data.
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

	/**
	 * Returns a List<Container> object with a containerId field matching the one 
	 * specified as a parameter.
	 * @param containerId long
	 * @return Container
	 */
	@Override
	public List<ContainerLog> findWithContainerId(long containerId) {
		AssertUtil.assertNotNull(containerId, "Parameter 'containerId' cannot be null!");
		Criteria criteria = newCriteria();
		criteria.createAlias("container", "container");
		criteria.add(Restrictions.eq("container.id", containerId));
		List<ContainerLog> logs = criteria.list();
		return (logs == null)? Collections.<ContainerLog>emptyList() : logs;
	}
	
	/**
	 * Returns a double of the average memory used by a Container. It accepts
	 * a long as a parameter, then passes that to another method to find the 
	 * average, which is then returned.
	 * @param containerId long
	 * @return double
	 */
	@Override
	public double getContainerLogAvgMemUsage(long containerId) {
		return getAverage(containerId, "memUsed");
	}

	/**
	 * Returns a double of the average CPU used by a Container. It accepts
	 * a long as a parameter, then passes that to another method to find the 
	 * average, which is then returned.
	 * @param containerId long
	 * @return double
	 */
	@Override
	public double getContainerLogAvgCpuUsage(long containerId) {
		return getAverage(containerId, "cpuUsed");
	}

	/**
	 * Returns a double of the average disk space used by a Container. It accepts
	 * a long as a parameter, then passes that to another method to find the 
	 * average, which is then returned.
	 * @param containerId long
	 * @return double
	 */
	@Override
	public double getContainerLogAvgDiskUsage(long containerId) {
		return getAverage(containerId, "filesystemUsed");
	}
	
	/**
	 * Returns a double after calculating the average usage of whatever data it
	 * is passed. This method is a utility called by all of the data-specific
	 * get average methods in the class.
	 * @param containerId long
	 * @param property String
	 * @return double
	 */
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
	/**
	 * Returns a List<Metric> from all of the containers with a specified ID, within a
	 * a particular amount of time, and with an average of a specified field.
	 * @param containerId long
	 * @param periodCount int
	 * @param fieldToAverage String
	 * @return List<Metric>
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
	/**
	 * Returns a List<Metric> from all of the containers with state fields greater-than
	 * or equal to certain user defined parameters.
	 * @param memThreshold long
	 * @param cpuThreshold long
	 * @param diskThreshold long
	 * @param periodCountInHours int
	 * @return List<Metric>
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
	/**
	 * Returns a List<Metric> from all of the containers with data within a specified
	 * amount of time.
	 * @param periodInHours int
	 * @return List<Metric>
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

	/* (non-Javadoc)
	 * @see edu.depaul.armada.dao.ContainerLogDao#deleteOldData(int)
	 */
	@Override
	public void deleteOldData(int interval) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete from ContainerLog c where c.timestamp <= :timestamp");
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, interval * -1);
		
		query.setTimestamp("timestamp", new Timestamp(cal.getTimeInMillis()));
		query.executeUpdate();
	}
}
