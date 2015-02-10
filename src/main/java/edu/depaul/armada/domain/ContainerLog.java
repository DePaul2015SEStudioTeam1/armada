package edu.depaul.armada.domain;

/**
 * @author jplante jdavidson
 */
public class ContainerLog {

	private long id;
	private Container container;
	private String timestamp;
	private long memUsage;
	private long totalCpuUsage;
	private long filesystemUsage;
	private long memTotal;
	private long totalCpu;
	private long totalFilesystem;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
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
	
	public long getMemTotal(){
		return memTotal;
	}
	
	public void setMemTotal(long memTotal) {
		this.memTotal = memTotal;
	}
	
	public long getTotalCpu() {
		return totalCpu;
	}
	
	public void setTotalCpu(long totalCpu) {
		this.totalCpu = totalCpu;
	}
	
	public long getTotalFilesystem(){
		return totalFilesystem;
	}
	
	public void setTotalFilesystem(long totalFilesystem){
		this.totalFilesystem = totalFilesystem;
	}

}
