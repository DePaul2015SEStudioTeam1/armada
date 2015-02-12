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

import edu.depaul.armada.dao.ContainerDao;
import edu.depaul.armada.model.DashboardContainer;

/**
 * @author ptrzyna
 *
 */
@Controller
@RequestMapping("/containers")
public class ArmadaController {

	private ContainerDao containerDao;
	
	@Autowired
	public void setContainerDao(ContainerDao dao) {
		containerDao = dao;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public List<DashboardContainer> getAll() {
		return containerDao.getAllDashboardContainers();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<DashboardContainer> getPage(@PathVariable long id) {
		return containerDao.getDashboardContainers(id, 10);
	}
	
	
}
