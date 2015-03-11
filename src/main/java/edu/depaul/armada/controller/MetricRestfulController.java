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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.depaul.armada.model.Metric;
import edu.depaul.armada.model.ThresholdMetric;
import edu.depaul.armada.service.MetricService;

/**
 * Uses several Spring features, most centrally the @RequestMapping annotation. This sends web 
 * requests to specific pieces of the code, and then sends back the results through @ResponseBody. 
 * Specifically, this class gets all of the ThresholdMetric data set by the user, and provides
 * it do the dashboard charts.
 * @author ptrzyna
 */
@Controller
@RequestMapping("/metrics")
public class MetricRestfulController {

	private MetricService metricService;
	
	/**
	 * Creates a reference to the MetricService object that it is passed.
	 * @param metricService
	 */
	@Autowired
	public void setMetricService(MetricService metricService) {
		this.metricService = metricService;
	}
	
	/**
	 * Returns a list of all of the ThersholdMetric objects within the number of hours that 
	 * are passed to the method as a parameter.
	 * @param hours
	 * @return List<ThresholdMetric>
	 */
	@RequestMapping(value = "/thresholdStats/{hours}", method = RequestMethod.GET)
	@ResponseBody
	public List<ThresholdMetric> getThresholdStats(@PathVariable int hours) {
		return metricService.getMetrics(hours);
	}
	
	/**
	 * Returns a list of all of the Metric objects within the number of hours that are
	 * passed to the method as a parameter.
	 * @param hours
	 * @return List<Metric>
	 */
	@RequestMapping(value = "/containerCounts/{hours}", method = RequestMethod.GET)
	@ResponseBody
	public List<Metric> getContainerCounts(@PathVariable int hours) {
		return metricService.getContainerCount(hours);
	}
}
