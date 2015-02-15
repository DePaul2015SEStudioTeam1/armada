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
	 * Gets list of logs matching container id
	 * 
	 * @param containerId	id of the parent container of logs we want
	 * @return list of logs belonging to a container
	 */
	List<ContainerLog> findWithContainerId(long containerId);
	
	double getContainerLogAvgMemUsage(long containerId);
	double getContainerLogAvgCpuUsage(long containerId);
	double getContainerLogAvgFileSystemUsage(long containerId);
}
