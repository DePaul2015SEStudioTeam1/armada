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
 * Uses several Spring features, most centrally the @RequestMapping annotation. This sends web 
 * requests to specific pieces of the code, and then sends back the results through @ResponseBody. 
 * Specifically, this class gets either all, or one page worth of DashboardContainers. This 
 * controller was designed to provide DashboardContainer data to the dashboard itself.
 * @author ptrzyna
 *
 */
@Controller
@RequestMapping("/containers")
public class ContainerRestfulController {

	private ContainerDao containerDao;
	
	/**
	 * Creates a reference to the ContainerDao object that it is passed.
	 * @param dao
	 */
	@Autowired
	public void setContainerDao(ContainerDao dao) {
		containerDao = dao;
	}
	
	/**
	 * Returns a list of all of the DashboardContainer objects from the ContainerDao.
	 * @return List<DashboardContiner>
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public List<DashboardContainer> getAllDashboardContainers() {
		return containerDao.getAllDashboardContainers();
	}
	
	/**
	 * Returns a list of 10 DashboardContainer objects with the specific Container ID 
	 * from ContainerDao.
	 * @param id
	 * @return List<DashboardContainer>
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<DashboardContainer> getPageOfDashboardContainers(@PathVariable long id) {
		return containerDao.getDashboardContainers(id, 10);
	}
	
}
