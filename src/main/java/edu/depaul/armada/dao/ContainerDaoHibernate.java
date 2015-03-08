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
 * A class that implements the ContainerDao interface. It uses the Hibernate 
 * framework to interact with the application’s database. It exists to provide 
 * a way for the application to either store or retrieve Container data.
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
	/**
	 * Accepts a Container object, and uses Hibernate's saveOrUpdate method to save the
	 * most recent container data in the database.
	 * @param container Container
	 */
	@Override
	public void store(Container container) {
		AssertUtil.assertNotNull(container, "Container instance cannot be null!");
		if (container.getCAdvisorURL() == null || container.getCAdvisorURL().isEmpty()) {
			container.setCAdvisorURL("test");
		}
		sessionFactory.getCurrentSession().saveOrUpdate(container);
		sessionFactory.getCurrentSession().flush();
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.operations.dao.ContainerDao#getAll()
	 */
	/**
	 * Returns a List<Container> of all of the Container data in the database.
	 * @return List<Container>
	 */
	@Override
	public List<Container> getAll() {
		Query query = sessionFactory.getCurrentSession().createQuery("from Container");
		return query.list();
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.operations.dao.ContainerDao#get(long, int)
	 */
	/**
	 * Returns a List<Container> of a limited number of Containers with a specific id.
	 * @param id long
	 * @param int count
	 * @return List<Container>
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
	/**
	 * Returns a single Container object with a containerId field matching the one specified
	 * as a parameter.
	 * @param containerId long
	 * @return Container
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
	/**
	 * Returns a single Container object with a containerUniqueId field matching the one specified
	 * as a parameter.
	 * @param containerUniqueId String
	 * @return Container
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
	/**
	 * Returns a List<DashboardContainer> of all of the DashboardContainer objects
	 * stored in the database.
	 * @return List<DashboardContainer>
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
			if(logs == null || logs.isEmpty()) {
				continue;
			}
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
	/**
	 * Returns a List<DashboardContainer> of a limited number of DashboardContainers with a specific id.
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
