package edu.depaul.armada.domain;

/**
 * Created by jodavidson on 1/25/15.
 */
public class ContainerLog {

	private long id;
	private String containerId;
	private String timestamp;
	private long memUsage;
	private long totalCpuUsage;
	private long filesystemUsage;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public long getMemUsage() {
		return memUsage;
	}

	public void setMemUsage(long memUsage) {
		this.memUsage = memUsage;
	}

	public long getTotalCpuUsage() {
		return totalCpuUsage;
	}

	public void setTotalCpuUsage(long totalCpuUsage) {
		this.totalCpuUsage = totalCpuUsage;
	}

	public long getFilesystemUsage() {
		return filesystemUsage;
	}

	public void setFilesystemUsage(long filesystemUsage) {
		this.filesystemUsage = filesystemUsage;
	}

}
