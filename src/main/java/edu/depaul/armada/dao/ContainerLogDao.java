/**
 * 
 */
package edu.depaul.armada.dao;

import java.util.List;

import edu.depaul.armada.domain.ContainerLog;
import edu.depaul.armada.model.Metric;


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
	 * Gets logs for given container for a period of 24h in the past
	 * 
	 * @param containerId	id of the container to which the logs belong
	 * @param period	used to determine the time period for which we want to collect logs
	 * @return list of logs
	 */
	List<ContainerLog> findWithContainerIdAndPeriod(long containerId, int period);
	
	/**
	 * Gets a list of counts per hour based on specified countNumber
	 * 
	 * @stateThreshold	how many containers breached this threshold in the time interval
	 * @periodCountInHours	how many intervals do we want counts for
	 * @return list of counts of how many containers are in given state in given interval
	 */
	List<Metric> getStateCounts(long memThreshold, long cpuThreshold, long diskThreshold, int periodCountInHours);

	/**
	 * Gets a list of counts per hour based on specified countNumber
	 * 
	 * @param periodInHours	number of metrics
	 * @return list of containers per hour
	 */
	List<Metric> getContainerCounts(int periodInHours);
}
