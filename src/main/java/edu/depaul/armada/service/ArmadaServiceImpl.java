/**
 * 
 */
package edu.depaul.armada.service;

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
 * Concrete implementation of the OperationsService
 * 
 * @author John Plante
 */
@Transactional
public class ArmadaServiceImpl implements ArmadaService {
    
    private final Logger logger = Logger.getLogger(ArmadaServiceImpl.class);

	private ContainerDao containerDao;
	private ContainerLogDao containerLogDao;
	private PreferenceDao preferenceDao;
	
	public void setContainerDao(ContainerDao containerDao) {
		this.containerDao = containerDao;
	}
	
	public void setContainerLogDao(ContainerLogDao containerLogDao) {
		this.containerLogDao = containerLogDao;
	}
	
	public void setPreferenceDao(PreferenceDao preferenceDao) {
		this.preferenceDao = preferenceDao;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.service.ArmadaService#send(edu.depaul.armada.model.AgentContainer)
	 */
	@Override
	public void send(AgentContainerLog agentContainer) {
		// get container matching unique id
		Container container = containerDao.findWithContainerUniqueId(agentContainer.containerUniqueId); 
		if(container == null) {
			container = new Container();
			container.setCAdvisorURL(agentContainer.cAdvisorURL);
			container.setContainerUniqueId(agentContainer.containerUniqueId);
			container.setName(agentContainer.name);
		}
		// add entry
		ContainerLog log = new ContainerLog();
		log.setCpuUsed(agentContainer.cpuUsed);
		log.setCpuTotal(agentContainer.cpuTotal);
		log.setMemUsed(agentContainer.memUsed);
		log.setMemTotal(agentContainer.memTotal);
		log.setDiskUsed(agentContainer.diskUsed);
		log.setDiskTotal(agentContainer.diskTotal);
		log.setTimestamp(agentContainer.timestamp);
		
		container.addLog(log);
		// save container
		containerDao.store(container);
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.service.ArmadaService#send(java.util.List)
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
	@Override
	public void storePreferences(List<DashboardPreference> dashboardPreferences) {
		for (DashboardPreference preference : dashboardPreferences) {
			storePreference(preference);
		}
	}
	
	

}
