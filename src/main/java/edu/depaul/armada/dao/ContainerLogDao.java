/**
 * 
 */
package edu.depaul.armada.dao;

import java.util.List;

import edu.depaul.armada.domain.ContainerLog;


/**
 * Used for CRUD operations associated with ConatainerLogs
 * 
 * Created by jodavidson on 1/25/15.
 * Completed by jplante
 */
public interface ContainerLogDao {

	/**
	 * Stores a containerLog
	 *
	 * @param containerLog container instance we want to store
	 */
	void store(ContainerLog containerLog);
	
	/**
	 * Gets record matching container id
	 * 
	 * @param containerId	id of the container we want to retrieve
	 * @return containerLog instance matching the id
	 */
	List<ContainerLog> findWithContainerId(String containerId);
	long getContainerLogAvgMemUsage(String containerId);
	long getContainerLogAvgCpuUsage(String containerId);
	long getContainerLogAvgFileSystemUsage(String containerId);
}
