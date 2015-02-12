/**
 *
 */
package edu.depaul.armada.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
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

	/* (non-Javadoc)
	 * @see edu.depaul.armada.dao.ContainerLogDao#store(edu.depaul.armada.domain.ContainerLog)
	 */
	@Override
	public void store(ContainerLog containerLog) {
		AssertUtil.assertNotNull(containerLog, "ContainerLog instance cannot be null!");
		sessionFactory.getCurrentSession().saveOrUpdate(containerLog);
		sessionFactory.getCurrentSession().flush();
	}

	@Override
	public List<ContainerLog> findWithContainerId(long containerId) {
		AssertUtil.assertNotNull(containerId, "Parameter 'containerId' cannot be null!");
		Query query = sessionFactory.getCurrentSession().createQuery("from ContainerLog where id = :containerId");
		query.setLong("containerId", containerId);
		return castContainerLogList(ContainerLog.class, query.list());
	}
	
	@Override
	public long getContainerLogAvgMemUsage(long containerId) {
		List<ContainerLog> containerLogList = findWithContainerId(containerId);
		long avg = 0;
		if(!containerLogList.isEmpty()){
			for (ContainerLog cl : containerLogList){
				avg += cl.getMemUsed();
			}
			return avg/containerLogList.size();
		}
		return avg;
	}

	@Override
	public long getContainerLogAvgCpuUsage(long containerId) {
		List<ContainerLog> containerLogList = findWithContainerId(containerId);
		long avg = 0;
		if(!containerLogList.isEmpty()){
			for (ContainerLog cl : containerLogList){
				avg += cl.getCpuUsed();
			}
			return avg/containerLogList.size();
		}
		return avg;
	}

	@Override
	public long getContainerLogAvgFileSystemUsage(long containerId) {
		List<ContainerLog> containerLogList = findWithContainerId(containerId);
		long avg = 0;
		if(!containerLogList.isEmpty()){
			for (ContainerLog cl : containerLogList){
				avg += cl.getDiskUsed();
			}
			return avg/containerLogList.size();
		}
		return avg;
	}
	
	public static <T> List<ContainerLog> castContainerLogList(Class<? extends ContainerLog> ContainerLog, Collection<?> collection) {
	    List<ContainerLog> containerLogList = new ArrayList<ContainerLog>(collection.size());
	    for(Object containerLog: collection) {
	    	containerLogList.add(ContainerLog.cast(containerLog));
	    }
	    return containerLogList;
	}
	
}
