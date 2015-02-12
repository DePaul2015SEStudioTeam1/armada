package edu.depaul.armada.domain;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author ptrzyna, john davidson, jplante
 *
 */
public class Container {
	private long id;
	private String name;
	private String containerUniqueId;
	private String cAdvisorURL;
	private Set<ContainerLog> logs = new TreeSet<ContainerLog>();

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

	public Set<ContainerLog> getLogs() {
		return logs;
	}

	public void setLogs(Set<ContainerLog> logs) {
		this.logs = logs;
	}
	
	public void addLog(ContainerLog log) {
		log.setContainer(this);
		this.logs.add(log);
	}

}
