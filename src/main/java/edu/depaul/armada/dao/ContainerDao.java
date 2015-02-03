/**
 * 
 */
package edu.depaul.armada.dao;

import java.util.List;


/**
 * @author ptrzyna
 */
public interface ContainerDao<Container> {

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
	Container findWithContainerId(String containerId);
}
