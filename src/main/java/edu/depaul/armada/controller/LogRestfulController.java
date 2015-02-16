/**
 * 
 */
package edu.depaul.armada.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.depaul.armada.dao.ContainerLogDao;
import edu.depaul.armada.domain.Container;
import edu.depaul.armada.domain.ContainerLog;
import edu.depaul.armada.model.DashboardContainerLog;
import edu.depaul.armada.util.DateUtil;

/**
 * @author ptrzyna
 *
 */
@Controller
@RequestMapping("/logs")
public class LogRestfulController {

	private ContainerLogDao containerLogDao;
	
	@Autowired
	public void setContainerLogDao(ContainerLogDao dao) {
		containerLogDao = dao;
	}
	
	@RequestMapping(value = "/{containerId}", method = RequestMethod.GET)
	@ResponseBody
	public List<DashboardContainerLog> getLogsForPast24Hours(@PathVariable long containerId) {
		int period = 24;	// period of 24h in the past
		List<ContainerLog> logs = containerLogDao.findWithContainerIdAndPeriod(containerId, period);
		
		if(logs == null || logs.isEmpty()) {
			return Collections.emptyList();
		}
		
		List<DashboardContainerLog> results = new ArrayList<DashboardContainerLog>(logs.size());
		Container container = logs.get(0).getContainer();
		for(ContainerLog log : logs) {
			DashboardContainerLog temp = new DashboardContainerLog();
			temp.name = container.getName();
			temp.cpuTotal = log.getCpuTotal();
			temp.cpuUsed = log.getCpuUsed();
			temp.memTotal = log.getMemTotal();
			temp.memUsed = log.getMemUsed();
			temp.diskTotal = log.getDiskTotal();
			temp.diskUsed = log.getDiskUsed();
			temp.timestamp = DateUtil.toJson(log.getTimestamp());
			results.add(temp);
		}
		
		return results;
	}
}
