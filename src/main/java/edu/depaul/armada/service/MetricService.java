/**
 * 
 */
package edu.depaul.armada.service;

import java.util.List;

import edu.depaul.armada.model.ThresholdMetric;

/**
 * Used by log controller to provide metrics over time
 * 
 * @author ptrzyna
 */
public interface MetricService {
	
	public enum ContainerState {OK, WARN, ERR};

	/**
	 * Gives us a counts over time of how containers behaved
	 * 
	 * NOTE: for each hourly interval we will count how many containers breached a threshold
	 * 
	 * Example of returned data: 1am {up:3, error:4, warn:7}, 2am ...
	 * 
	 * @param periodInHours	how far back do we want to get data (each data point is for an hour interval)
	 * @return list of counts for a 24h period
	 */
	public List<ThresholdMetric> getMetrics(int periodInHours);
	
}
