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

import edu.depaul.armada.domain.ContainerLog;

/**
 * @author ptrzyna and jplante
 *
 */
@Repository
public class ContainerLogDaoHibernate implements ContainerLogDao<ContainerLog> {

	private SessionFactory sessionFactory;

	@Autowired
	public ContainerLogDaoHibernate(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.dao.ContainerLogDao#store(edu.depaul.armada.domain.ContainerLog)
	 */
	@Override
	public void store(ContainerLog containerLog) {
		sessionFactory.getCurrentSession().saveOrUpdate(containerLog);
		sessionFactory.getCurrentSession().flush();
	}

	@Override
	public List<ContainerLog> findWithContainerId(String containerId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from ContainerLog where container_id = :containerId");
		query.setString("containerId", containerId);
		return castContainerLogList(ContainerLog.class, query.list());
	}

	
	@Override
	public long getContainerLogAvgMemUsage(String containerId) {
		List<ContainerLog> containerLogList = findWithContainerId(containerId);
		long avg = 0;
		if(!containerLogList.isEmpty()){
			for (ContainerLog cl : containerLogList){
				avg += cl.getMemUsage();
			}
			return avg/containerLogList.size();
		}
		return avg;
	}

	@Override
	public long getContainerLogAvgCpuUsage(String containerId) {
		List<ContainerLog> containerLogList = findWithContainerId(containerId);
		long avg = 0;
		if(!containerLogList.isEmpty()){
			for (ContainerLog cl : containerLogList){
				avg += cl.getTotalCpuUsage();
			}
			return avg/containerLogList.size();
		}
		return avg;
	}

	@Override
	public long getContainerLogAvgFileSystemUsage(String containerId) {
		List<ContainerLog> containerLogList = findWithContainerId(containerId);
		long avg = 0;
		if(!containerLogList.isEmpty()){
			for (ContainerLog cl : containerLogList){
				avg += cl.getFilesystemUsage();
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
