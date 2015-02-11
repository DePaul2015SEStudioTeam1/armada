package edu.depaul.armada.domain;

import java.sql.Timestamp;

/**
 * @author jplante jdavidson
 */
public class ContainerLog {

	private long id;
	private Container container;
	private Timestamp timestamp;
	private long cpuUsed;
	private long cpuTotal;
	private long diskUsed;
	private long diskTotal;
	private long memUsed;
	private long memTotal;

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

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public long getCpuUsed() {
		return cpuUsed;
	}

	public void setCpuUsed(long cpuUsed) {
		this.cpuUsed = cpuUsed;
	}

	public long getCpuTotal() {
		return cpuTotal;
	}

	public void setCpuTotal(long cpuTotal) {
		this.cpuTotal = cpuTotal;
	}

	public long getMemUsed() {
		return memUsed;
	}

	public void setMemUsed(long memUsed) {
		this.memUsed = memUsed;
	}

	public long getMemTotal() {
		return memTotal;
	}

	public void setMemTotal(long memTotal) {
		this.memTotal = memTotal;
	}

	public long getDiskUsed() {
		return diskUsed;
	}

	public void setDiskUsed(long diskUsed) {
		this.diskUsed = diskUsed;
	}

	public long getDiskTotal() {
		return diskTotal;
	}

	public void setDiskTotal(long diskTotal) {
		this.diskTotal = diskTotal;
	}	

}
