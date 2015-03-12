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

import java.util.ArrayList;
import java.util.List;

import edu.depaul.armada.dao.ContainerDao;
import edu.depaul.armada.dao.ContainerLogDao;
import edu.depaul.armada.dao.PreferenceDao;
import edu.depaul.armada.model.Metric;
import edu.depaul.armada.model.ThresholdMetric;

/**
 * Used by log controller to provide metrics over time from containers for use by the dashboard.
 * 
 * @author ptrzyna
 */
public class MetricServiceImpl implements MetricService {
	
	private static final String MEM_THRESHOLD = "memory_threshold";
	private static final String CPU_THRESHOLD = "cpu_threshold";
	private static final String DISK_THRESHOLD = "disk_threshold";
	private static final String REFRESH_INTERVAL = "refresh_page";
	
	private ContainerDao containerDao;
	private ContainerLogDao containerLogDao;
	private PreferenceDao preferenceDao;
	
	/**
	 * Creates a reference to the ContainerDao object that it is passed.
	 * @param containerDao
	 */
	public void setContainerDao(ContainerDao containerDao) {
		this.containerDao = containerDao;
	}
	
	/**
	 * Creates a reference to the ContainerLogDao object that it is passed.
	 * @param containerLogDao
	 */
	public void setContainerLogDao(ContainerLogDao containerLogDao) {
		this.containerLogDao = containerLogDao;
	}
	
	/**
	 * Creates a reference to the PreferenceDao object that it is passed.
	 * @param preferenceDao
	 */
	public void setPreferenceDao(PreferenceDao preferenceDao) {
		this.preferenceDao = preferenceDao;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.service.MetricService#getContainerMetrics(int)
	 */
	/**
	 * Returns an ArrayList<ThresholdMetric> full of data on the performance of containers. First, it creates local 
	 * values and assigns them the thresholds for memory, CPU, and disk use. Then it finds out how many Containers 
	 * are running. Next, it collects a List<Metric> of all of the containers that are 75% or more of the way to 
	 * breaching their use defined thresholds, and then all of the containers that have breached those thresholds. 
	 * From there, it creates the ArrayList<ThresholdMetric> it will return, and populates it with an hour-by-hour 
	 * breakdown of what containers are in which state. Once that list is complete, it is returned.
	 * @param periodInHours 
	 * @return List<ThresholdMetric> 
	 */
	@Override
	public List<ThresholdMetric> getMetrics(int periodInHours) {
		long mem = preferenceDao.findWithPreferenceName(MEM_THRESHOLD).getValue();
		long cpu = preferenceDao.findWithPreferenceName(CPU_THRESHOLD).getValue();
		long disk = preferenceDao.findWithPreferenceName(DISK_THRESHOLD).getValue();
		long refreshInterval = preferenceDao.findWithPreferenceName(REFRESH_INTERVAL).getValue();
		
		List<Metric> containerCount = getContainerCount(periodInHours);

		List<Metric> warn = containerLogDao.getStateCounts(Double.valueOf(mem*0.75).longValue(), 
				Double.valueOf(cpu*0.75).longValue(), 
				Double.valueOf(disk*0.75).longValue(), 
				refreshInterval,
				periodInHours);	// breached warning threshold
		List<Metric> err = containerLogDao.getStateCounts(mem, cpu, disk, refreshInterval, periodInHours);	// breached threshold

		List<ThresholdMetric> metrics = new ArrayList<ThresholdMetric>(periodInHours);
		for(int i=0; i<periodInHours; i++) {
			ThresholdMetric temp = new ThresholdMetric();
			Metric errTemp = err.get(i);
			Metric warnTemp = warn.get(i);
			temp.setPeriod(errTemp.getHour());
			temp.setError(errTemp.getValue());
			temp.setWarn(warnTemp.getValue() - errTemp.getValue());
			temp.setOk(containerCount.get(i).getValue() - (temp.getError() + temp.getWarn()));
			metrics.add(temp);
		}
		return metrics;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.service.MetricService#getContainerCount(int)
	 */
	/**
	 * Returns a List<Metric> of metrics from the container for the specified time period.
	 * @param periodInHours
	 * @return List<Metric>
	 */
	@Override
	public List<Metric> getContainerCount(int periodInHours) {
		List<Metric> metrics = containerLogDao.getContainerCounts(periodInHours);
		return metrics;
	}
}
