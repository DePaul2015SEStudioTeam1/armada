/**
 * 
 */
package edu.depaul.armada.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.depaul.armada.domain.Container;
import edu.depaul.armada.domain.ContainerLog;
import edu.depaul.armada.model.DashboardContainer;
import edu.depaul.armada.util.AssertUtil;

/**
 * @author ptrzyna
 *
 */
@Transactional
@Repository
public class ContainerDaoHibernate implements ContainerDao {

	private SessionFactory sessionFactory;
	
	public ContainerDaoHibernate() {}
	
	@Autowired
	public ContainerDaoHibernate(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.dao.ContainerDao#store(edu.depaul.armada.domain.Container)
	 */
	@Override
	public void store(Container container) {
		AssertUtil.assertNotNull(container, "Container instance cannot be null!");
		sessionFactory.getCurrentSession().saveOrUpdate(container);
		sessionFactory.getCurrentSession().flush();
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.operations.dao.ContainerDao#getAll()
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
	 * @see edu.depaul.armada.operations.dao.ContainerDao#findWithDockerId(java.lang.String)
	 */
	@Override
	public Container findWithContainerId(long containerId) {
		AssertUtil.assertNotNull(containerId, "Parameter 'containerId' cannot be null!");
		Query query = sessionFactory.getCurrentSession().createQuery("from Container where id = :containerId");
		query.setLong("containerId", containerId);
		return (Container) query.uniqueResult();
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.dao.ContainerDao#findWithContainerUniqueId(java.lang.String)
	 */
	@Override
	public Container findWithContainerUniqueId(String containerUniqueId) {
		AssertUtil.assertNotNull(containerUniqueId, "Parameter 'containerUniqueId' cannot be null!");
		Query query = sessionFactory.getCurrentSession().createQuery("from Container where containerUniqueId = :containerUniqueId");
		query.setString("containerUniqueId", containerUniqueId);
		return (Container) query.uniqueResult();
	}
	
	/* (non-Javadoc)
	 * @see edu.depaul.armada.operations.dao.ContainerDao#getAll()
	 */
	@Override
	public List<DashboardContainer> getAllDashboardContainers() {
		Query query = sessionFactory.getCurrentSession().createQuery("from Container");
		List<Container> containers = query.list();
		List<DashboardContainer> result = new ArrayList<DashboardContainer>(containers.size());
		for(Container container : containers) {
			DashboardContainer temp = new DashboardContainer();
			temp.name = container.getName();
			temp.containerId = container.getId();
			temp.containerUniqueId = container.getContainerUniqueId();
			temp.cAdvisorURL = container.getCAdvisorURL();
			List<ContainerLog> logs = container.getLogs();
			ContainerLog[] logArr = logs.toArray(new ContainerLog[logs.size()]);
			int index = logArr.length-1;
			temp.cpuUsed = logArr[index].getCpuUsed();
			temp.cpuTotal = logArr[index].getCpuTotal();
			temp.memUsed = logArr[index].getMemUsed();
			temp.memTotal = logArr[index].getMemTotal();
			temp.diskUsed = logArr[index].getDiskUsed();
			temp.diskTotal = logArr[index].getDiskTotal();
			result.add(temp);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.operations.dao.ContainerDao#get(long, int)
	 */
	@Override
	public List<DashboardContainer> getDashboardContainers(long id, int count) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Container where id >= :id");
		query.setLong("id", id);
		query.setMaxResults(count);
		List<Container> containers = query.list();
		List<DashboardContainer> result = new ArrayList<DashboardContainer>(containers.size());
		for(Container container : containers) {
			DashboardContainer temp = new DashboardContainer();
			temp.name = container.getName();
			temp.containerId = container.getId();
			temp.containerUniqueId = container.getContainerUniqueId();
			temp.cAdvisorURL = container.getCAdvisorURL();
			List<ContainerLog> logs = container.getLogs();
			ContainerLog[] logArr = logs.toArray(new ContainerLog[logs.size()]);
			int index = logArr.length-1;
			temp.cpuUsed = logArr[index].getCpuUsed();
			temp.cpuTotal = logArr[index].getCpuTotal();
			temp.memUsed = logArr[index].getMemUsed();
			temp.memTotal = logArr[index].getMemTotal();
			temp.diskUsed = logArr[index].getDiskUsed();
			temp.diskTotal = logArr[index].getDiskTotal();
			result.add(temp);
		}
		return result;
	}
}
