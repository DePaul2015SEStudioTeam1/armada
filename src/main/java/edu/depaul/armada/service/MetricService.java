/*
 * The MIT License (MIT)
 * 
 * Copyright (c) <year> <copyright holders> 
 * 
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
package edu.depaul.armada.service;

import java.util.List;

import edu.depaul.armada.model.Metric;
import edu.depaul.armada.model.ThresholdMetric;

/**
 * An interface which specifies methods used for storing and receiving Container metrics over time.
 * 
 * @author ptrzyna
 */
public interface MetricService {
	
	public enum ContainerState {OK, WARN, ERR};

	/**
	 * Gives us counts over time of how containers behaved
	 * 
	 * NOTE: for each hourly interval we will count how many containers breached a threshold
	 * 
	 * Example of returned data: 1am {up:3, error:4, warn:7}, 2am ...
	 * 
	 * @param periodInHours	how far back do we want to get data (each data point is for an hour interval)
	 * @return list of counts for a 24h period
	 */
	public List<ThresholdMetric> getMetrics(int periodInHours);
	
	/**
	 * Gives us counts over time of how many containers were running
	 * 
	 * NOTE: for each hourly interval we will count how many containers are running
	 * 
	 * Example of returned data: 1am {count:3}
	 * 
	 * @param periodInHours	how far back do we want to get data (each data point is for an hour interval)
	 * @return list of counts for a 24h period
	 */
	public List<Metric> getContainerCount(int periodInHours);
	
}
