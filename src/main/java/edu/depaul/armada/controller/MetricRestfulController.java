/**
 * 
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
 * Used to provide metrics to the dashboard charts
 * 
 * @author ptrzyna
 */
@Controller
@RequestMapping("/metrics")
public class MetricRestfulController {

	private MetricService metricService;
	
	@Autowired
	public void setMetricService(MetricService metricService) {
		this.metricService = metricService;
	}
	
	@RequestMapping(value = "/thresholdStats/{hours}", method = RequestMethod.GET)
	@ResponseBody
	public List<ThresholdMetric> getThresholdStats(@PathVariable int hours) {
		return metricService.getMetrics(hours);
	}
	
	@RequestMapping(value = "/containerCounts/{hours}", method = RequestMethod.GET)
	@ResponseBody
	public List<Metric> getContainerCounts(@PathVariable int hours) {
		return metricService.getContainerCount(hours);
	}
}
