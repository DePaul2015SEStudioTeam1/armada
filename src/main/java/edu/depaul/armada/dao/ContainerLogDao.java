/*
 * The MIT License (MIT)
 * Copyright (c) <year> <copyright holders> 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
	 * Returns a List<Container> object with a containerId field matching the one 
	 * specified as a parameter.
	 * @param containerId	id of the parent container of logs we want
	 * @return list of logs belonging to a container
	 */
	List<ContainerLog> findWithContainerId(long containerId);
	
	/**
	 * Returns a double of the average memory used by a Container. It accepts
	 * a long as a parameter, then passes that to another method to find the 
	 * average, which is then returned.
	 * @param containerId	container for which we want average memory usage
	 * @return average of memory usage
	 */
	double getContainerLogAvgMemUsage(long containerId);
	
	/**
	 * Returns a double of the average CPU used by a Container. It accepts
	 * a long as a parameter, then passes that to another method to find the 
	 * average, which is then returned.
	 * @param containerId	container for which we want average cpu usage
	 * @return average of cpu usage
	 */
	double getContainerLogAvgCpuUsage(long containerId);
	
	/**
	 * Returns a double of the average disk space used by a Container. It accepts
	 * a long as a parameter, then passes that to another method to find the 
	 * average, which is then returned.
	 * @param containerId	container for which we want average disk usage
	 * @return average of disk usage
	 */
	double getContainerLogAvgDiskUsage(long containerId);

	/**
	 * Returns a List<Metric> from all of the containers with a specified ID, within a
	 * a particular amount of time, and with an average of a specified field.
	 * @param containerId	id of the container to which the logs belong
	 * @param period	used to determine the time period for which we want to collect logs
	 * @return list of logs
	 */
	List<Metric> findWithContainerIdAndPeriod(long containerId, int periodCount, String fieldToAverage);
	
	/**
	 * Returns a List<Metric> from all of the containers with state fields greater-than
	 * or equal to certain user defined parameters.
	 * @stateThreshold	how many containers breached this threshold in the time interval
	 * @periodCountInHours	how many intervals do we want counts for
	 * @return list of counts of how many containers are in given state in given interval
	 */
	List<Metric> getStateCounts(long memThreshold, long cpuThreshold, long diskThreshold, int periodCountInHours);

	/**
	 * Returns a List<Metric> from all of the containers with data within a specified
	 * amount of time.
	 * @param periodInHours	number of metrics
	 * @return list of containers per hour
	 */
	List<Metric> getContainerCounts(int periodInHours);
	
	/**
	 * Deletes data older than given interval in hours specified in the parameter it is passed.
	 * @param interval	interval in hours
	 */
	void deleteOldData(int interval);
}
