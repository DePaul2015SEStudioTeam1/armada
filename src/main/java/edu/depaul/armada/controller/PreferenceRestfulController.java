package edu.depaul.armada.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.depaul.armada.dao.PreferenceDao;
import edu.depaul.armada.model.DashboardPreference;


@Controller
@RequestMapping("/preferences")
public class PreferenceRestfulController {
	
	private PreferenceDao preferenceDao;
	
	@Autowired
	public void setPreferenceDao(PreferenceDao dao) {
		preferenceDao = dao;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public List<DashboardPreference> getAll() {
		return preferenceDao.getAll();
	}
	
	
}
