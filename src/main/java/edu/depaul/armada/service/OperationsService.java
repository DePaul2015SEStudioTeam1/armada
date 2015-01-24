/**
 * 
 */
package edu.depaul.armada.service;

import java.util.List;


/**
 * Used to interact with the operations db.
 * 
 * @author Paul A. Trzyna
 */
public interface OperationsService<Container> {

	/**
	 * Inserts a new Container instance or updates the existing one
	 * if it already exists.
	 * 
	 * @param container	container we want to save/update
	 */
	void store(Container container);

	/**
	 * Gets all Containers in the db
	 * 
	 * @return all containers in the db
	 */
	List<Container> getAllContainers();
	
	/**
	 * Gets a desired set of records from the db, can be used for pagination
	 * 
	 * @param id	start id used for determining the start index of returned records
	 * @param count	number of records we want returned starting at the offset
	 * @return list of containers
	 */
	List<Container> get(long id, int count);
	
}
