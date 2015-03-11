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
package edu.depaul.armada.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.depaul.armada.dao.ContainerLogDao;
import edu.depaul.armada.model.ContainerMetric;
import edu.depaul.armada.model.Metric;

/**
 * Uses several Spring features, most centrally the @RequestMapping annotation. This sends web requests 
 * to specific pieces of the code, and then sends back the results through @ResponseBody. 
 * Specifically, this class gets all of the DashboardContainerLogs from the past 24 hours.
 * @author ptrzyna
 *
 */
@Controller
@RequestMapping("/logs")
public class LogRestfulController {

	private ContainerLogDao containerLogDao;
	
	/**
	 * Creates a reference to the ContainerLogDao object that it is passed.
	 * @param dao
	 */
	@Autowired
	public void setContainerLogDao(ContainerLogDao dao) {
		containerLogDao = dao;
	}
	
	/**
	 * Returns a list of all of the DashboardContainerLog objects with a specific Container ID 
	 * from the ContainerLogDao with timestamps within the past 24 hours.
	 * @param containerId
	 * @return
	 */
	@RequestMapping(value = "/{containerId}", method = RequestMethod.GET)
	@ResponseBody
	public List<ContainerMetric> getLogsForPast24Hours(@PathVariable long containerId) {
		int period = 24;	// period of 24h in the past
		List<Metric> cpuAverages = containerLogDao.findWithContainerIdAndPeriod(containerId, period, "cpuUsed");
		List<Metric> memAverages = containerLogDao.findWithContainerIdAndPeriod(containerId, period, "memUsed");
		List<Metric> diskAverages = containerLogDao.findWithContainerIdAndPeriod(containerId, period, "diskUsed");
		
		List<ContainerMetric> results = new ArrayList<ContainerMetric>(period);
		
		for(int i=0; i<period; i++) {
			ContainerMetric temp = new ContainerMetric();
			temp.setHour(cpuAverages.get(i).getHour());
			temp.setCpu(cpuAverages.get(i).getValue());
			temp.setDisk(diskAverages.get(i).getValue());
			temp.setMem(memAverages.get(i).getValue());
			
			results.add(temp);
		}
		return results;
	}
}
