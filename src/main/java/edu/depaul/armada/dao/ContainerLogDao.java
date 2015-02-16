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
	
	/**
	 * Calculates the average memory use
	 * 
	 * @param containerId	container for which we want average memory usage
	 * @return average of memory usage
	 */
	double getContainerLogAvgMemUsage(long containerId);
	
	/**
	 * Calculates the average cpu use
	 * 
	 * @param containerId	container for which we want average cpu usage
	 * @return average of cpu usage
	 */
	double getContainerLogAvgCpuUsage(long containerId);
	
	/**
	 * Calculates the average disk use
	 * 
	 * @param containerId	container for which we want average disk usage
	 * @return average of disk usage
	 */
	double getContainerLogAvgDiskUsage(long containerId);
	
	/**
	 * Gets a list of log entries for given container. These entries are for the past 24h.
	 * 
	 * @return list of log entries
	 */
	List<ContainerLog> getLogs(long containerId);
}
