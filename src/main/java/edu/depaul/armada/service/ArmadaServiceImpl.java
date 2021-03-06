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

import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import edu.depaul.armada.dao.ContainerDao;
import edu.depaul.armada.dao.ContainerLogDao;
import edu.depaul.armada.dao.PreferenceDao;
import edu.depaul.armada.domain.Container;
import edu.depaul.armada.domain.ContainerLog;
import edu.depaul.armada.domain.Preference;
import edu.depaul.armada.model.AgentContainerLog;
import edu.depaul.armada.model.DashboardPreference;

/**
 * Concrete implementation of the OperationsService. Exists to process data from agents monitoring containers, 
 * so that they can be used for monitoring, and to set user preference values.
 * 
 * @author John Plante
 */
@Transactional
public class ArmadaServiceImpl implements ArmadaService {
    
    private final Logger logger = Logger.getLogger(ArmadaServiceImpl.class);

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
	 * @see edu.depaul.armada.service.ArmadaService#send(edu.depaul.armada.model.AgentContainer)
	 */
	/**
	 * First this log checks to see if there is an existing ContainerDao with the same unique ID. 
	 * If not, a new container is created. After that, a new log is created, and its fields are set. 
	 * From there the log is added to the existing (or newly created) container and stored (for more 
	 * on storing Containers, see implementation in ContainerDaoHibernate.java).
	 * @param agentContainer
	 */
	@Override
	public void send(AgentContainerLog agentContainer) {
		
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		
		// get container matching unique id
		Container container = containerDao.findWithContainerUniqueId(agentContainer.containerUniqueId);
		if(container == null) {
			container = new Container();
			container.setCAdvisorURL(agentContainer.cAdvisorURL);
			container.setContainerUniqueId(agentContainer.containerUniqueId);
			container.setName(agentContainer.name);
		}
		container.setTimestamp(currentTime);
		// add entry
		ContainerLog log = new ContainerLog();
		log.setCpuUsed(agentContainer.cpuUsed);
		log.setCpuTotal(agentContainer.cpuTotal);
		log.setMemUsed(agentContainer.memUsed);
		log.setMemTotal(agentContainer.memTotal);
		log.setDiskUsed(agentContainer.diskUsed);
		log.setDiskTotal(agentContainer.diskTotal);
		log.setTimestamp(currentTime);
		
		container.addLog(log);
		// save container
		containerDao.store(container);
		
		logger.info("-- Inserting new logs with timestamp " + currentTime);
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.service.ArmadaService#send(java.util.List)
	 */
	/**
	 * Accepts a list of AgentContainerLogs, extracts each individual log, and sends them one by one 
	 * to the preceding send method.
	 * @param logs
	 */
	@Override
	public void send(List<AgentContainerLog> logs) {
		for(AgentContainerLog log : logs) {
			send(log);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see edu.depaul.armada.service.ArmadaService#storePreference(edu.depaul.armada.model.DashboardPreference)
	 */
	/**
	 * Accepts user preferences from the dashboard, then checks to see if this preference object has 
	 * already been created and stored. If so, it stores this update. If not, it creates a new Preference 
	 * object and stores that (for more on storing Preferences see implementation in PreferenceDaoHibernate.java).
	 * @param dashboardPreference
	 */
	@Override
	public void storePreference(DashboardPreference dashboardPreference) {
		// get container matching unique id
		Preference preference = preferenceDao.findWithPreferenceName(dashboardPreference.name); 
		if(preference == null) {
			preference = new Preference();
			preference.setName(dashboardPreference.name);
			preference.setValue(dashboardPreference.value);
		}
		// save preference
		preferenceDao.storePreference(preference);		
	}

	/*
	 * (non-Javadoc)
	 * @see edu.depaul.armada.service.ArmadaService#storePreferences(java.util.List)
	 */
	/**
	 * Accepts a list of DashboardPreferences from the dashboard, as defined by the user, removes each list
	 * item, and sends it to the storePreference(DashboardPreference) method above.
	 * @param dashboardPreference
	 */
	@Override
	public void storePreferences(List<DashboardPreference> dashboardPreferences) {
		for (DashboardPreference preference : dashboardPreferences) {
			storePreference(preference);
		}
	}
}
