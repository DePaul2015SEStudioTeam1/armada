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

import edu.depaul.armada.model.Container;
import edu.depaul.armada.service.ArmadaService;

/**
 * @author ptrzyna
 *
 */
@Controller
@RequestMapping("/containers")
public class ArmadaController {

	@Autowired private ArmadaService armadaService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public List<Container> getAll() {
		return armadaService.getAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Container> getPage(@PathVariable long id) {
		return armadaService.get(id, 10);
	}
	
	
}
