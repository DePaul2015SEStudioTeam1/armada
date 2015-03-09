/**
 * 
 */
package edu.depaul.armada.dao;

import java.util.List;

import edu.depaul.armada.domain.Container;
import edu.depaul.armada.model.DashboardContainer;


/**
 * Used for CRUD operations associated with Container instances
 * 
 * @author ptrzyna
 */
public interface ContainerDao {

	/**
	 * Accepts a Container object, and uses Hibernate's saveOrUpdate method to save the
	 * most recent container data in the database.
	 * @param container	Container instance we want to store
	 */
	void store(Container container);
	
	/**
	 * Returns a List<DashboardContainer> object.
	 * @return List<DashboardContainer>
	 */
	List<DashboardContainer> getAllDashboardContainers();
	
	/**
	 * Accepts a long and an int, returns a List<DashboardContainer> object.
	 * @param id long
	 * @param count int
	 * @return List<DashboardContainer>
	 */
	List<DashboardContainer> getDashboardContainers(long id, int count);

	/**
	 * Returns a List<Container> object.
	 * @return List<Container>
	 */
	List<Container> getAll();
	
	/**
	 * Accepts a long and an int, and returns a List<Container> object of the Container data in the database.
	 * @param id long
	 * @param count int
	 * @return List<Container>
	 */
	List<Container> get(long id, int count);
	
	/**
	 * Returns a single Container object with a containerId field matching the one specified
	 * as a parameter.
	 * @param containerId	id of the container we want to retrieve
	 * @return container instance matching the id
	 */
	Container findWithContainerId(long containerId);

	/**
	 * Returns a single Container object with a containerUniqueId field matching the one specified
	 * as a parameter.
	 * @param containerUniqueId	id used to find the container
	 * @return Container matching the id
	 */
	Container findWithContainerUniqueId(String containerUniqueId);
}
