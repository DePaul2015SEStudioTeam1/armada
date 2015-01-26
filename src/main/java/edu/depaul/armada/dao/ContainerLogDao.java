
package edu.depaul.armada.dao;

import java.util.List;


/**
 * Created by jodavidson on 1/25/15.
 */
public interface ContainerLogDao<ContainerLog> {

	/**
	 * Stores a containerLog
	 *
	 * @param containerLog container instance we want to store
	 */
	void store(ContainerLog containerLog);

}
