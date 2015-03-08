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
	 * Stores a container
	 * 
	 * @param container	container instance we want to store
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
	 * Accepts a long and an int, and returns a List<Container> object.
	 * @param id long
	 * @param count int
	 * @return List<Container>
	 */
	List<Container> get(long id, int count);
	
	/**
	 * Gets record matching container id
	 * 
	 * @param containerId	id of the container we want to retrieve
	 * @return container instance matching the id
	 */
	Container findWithContainerId(long containerId);

	/**
	 * Finds a Container instance with given id
	 * 
	 * @param containerUniqueId	id used to find the container
	 * @return Container matching the id
	 */
	Container findWithContainerUniqueId(String containerUniqueId);
}
