/**
 * 
 */
package edu.depaul.armada.service;

/**
 * Used to interact with the operations db.
 * 
 * @author Paul A. Trzyna
 */
public interface CollectorService<Container> {

	/**
	 * Inserts a new Container instance or updates the existing one
	 * if it already exists.
	 * 
	 * @param container	container we want to save/update
	 */
	void store(Container container);
	
}
