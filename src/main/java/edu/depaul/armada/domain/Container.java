package edu.depaul.armada.domain;

import java.util.List;

/**
 * @author ptrzyna, john davidson, jplante
 *
 */
public class Container {
	private long id;
	private String name;
	private String containerUniqueId;
	private String cAdvisorURL;
	private List<ContainerLog> logs;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContainerUniqueId() {
		return containerUniqueId;
	}

	public void setContainerUniqueId(String containerUniqueId) {
		this.containerUniqueId = containerUniqueId;
	}

	public String getCAdvisorURL() {
		return cAdvisorURL;
	}

	public void setCAdvisorURL(String cAdvisorURL) {
		this.cAdvisorURL = cAdvisorURL;
	}

	public List<ContainerLog> getLogs() {
		return logs;
	}

	public void setLogs(List<ContainerLog> logs) {
		this.logs = logs;
	}
	
	public void addLog(ContainerLog log) {
		log.setContainer(this);
		this.logs.add(log);
	}

}
