/**
 * 
 */
package edu.depaul.armada.dao;

import java.util.List;

import edu.depaul.armada.domain.Container;


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
	
	List<Container> getAll();
	
	List<Container> get(long id, int count);
	
	/**
	 * Gets record matching container id
	 * 
	 * @param containerId	id of the container we want to retrieve
	 * @return container instance matching the id
	 */
	Container findWithContainerId(long containerId);
}
