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

import edu.depaul.operations.model.Container;
import edu.depaul.operations.service.OperationsService;

/**
 * @author ptrzyna
 *
 */
@Controller
@RequestMapping("/containers")
public class OperationsController {

	@Autowired private OperationsService<Container> operationsService;
	
	/**
	 * @param operationsService the operationsService to set
	 */
	public void setOperationsService(OperationsService<Container> operationsService) {
		this.operationsService = operationsService;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public List<Container> getAll() {
		return operationsService.getAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Container> getPage(@PathVariable long id) {
		return operationsService.get(id, 10);
	}
	
	
}
