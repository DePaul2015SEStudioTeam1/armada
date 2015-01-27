package edu.depaul.armada.model;

import java.io.Serializable;

/**
 * Model object representing a docker container
 *
 */
public class Container implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private String containerId;
	private String cAdvisorURL;
	private long memLimit;
	private long cpuLimit;
	private long filesystemCapacity;

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

	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public String getcAdvisorURL() {
		return cAdvisorURL;
	}

	public void setcAdvisorURL(String cAdvisorURL) {
		this.cAdvisorURL = cAdvisorURL;
	}

	public long getMemLimit() {
		return memLimit;
	}

	public void setMemLimit(long memLimit) {
		this.memLimit = memLimit;
	}

	public long getCpuLimit() {
		return cpuLimit;
	}

	public void setCpuLimit(long cpuLimit) {
		this.cpuLimit = cpuLimit;
	}

	public long getFilesystemCapacity() {
		return filesystemCapacity;
	}

	public void setFilesystemCapacity(long filesystemCapacity) {
		this.filesystemCapacity = filesystemCapacity;
	}

}
