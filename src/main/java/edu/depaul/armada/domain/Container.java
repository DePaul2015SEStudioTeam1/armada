/**
 * 
 */
package edu.depaul.operations.domain;

/**
 * @author ptrzyna
 *
 */
public class Container {
	private long id;
	private String agentId;
	private long memTotal;
	private long memUsed;
	private long memFree;
	private String osDescription;
	private String osName;
	private String osDataModel;
	private String primaryIpAddress;
	private String primaryMacAddress;
	private String hostName;
	private String cpuVendor;
	private String cpuModel;
	private int cpuCount;
	private long diskSpaceTotal;
	private long diskSpaceUsed;
	private long diskSpaceFree;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public long getMemTotal() {
		return memTotal;
	}

	public void setMemTotal(long memTotal) {
		this.memTotal = memTotal;
	}

	public long getMemUsed() {
		return memUsed;
	}

	public void setMemUsed(long memUsed) {
		this.memUsed = memUsed;
	}

	public long getMemFree() {
		return memFree;
	}

	public void setMemFree(long memFree) {
		this.memFree = memFree;
	}

	public String getOsDescription() {
		return osDescription;
	}

	public void setOsDescription(String osDescription) {
		this.osDescription = osDescription;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getOsDataModel() {
		return osDataModel;
	}

	public void setOsDataModel(String osDataModel) {
		this.osDataModel = osDataModel;
	}

	public String getPrimaryIpAddress() {
		return primaryIpAddress;
	}

	public void setPrimaryIpAddress(String primaryIpAddress) {
		this.primaryIpAddress = primaryIpAddress;
	}

	public String getPrimaryMacAddress() {
		return primaryMacAddress;
	}

	public void setPrimaryMacAddress(String primaryMacAddress) {
		this.primaryMacAddress = primaryMacAddress;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getCpuVendor() {
		return cpuVendor;
	}

	public void setCpuVendor(String cpuVendor) {
		this.cpuVendor = cpuVendor;
	}

	public String getCpuModel() {
		return cpuModel;
	}

	public void setCpuModel(String cpuModel) {
		this.cpuModel = cpuModel;
	}

	public int getCpuCount() {
		return cpuCount;
	}

	public void setCpuCount(int cpuCount) {
		this.cpuCount = cpuCount;
	}

	public long getDiskSpaceTotal() {
		return diskSpaceTotal;
	}

	public void setDiskSpaceTotal(long diskSpaceTotal) {
		this.diskSpaceTotal = diskSpaceTotal;
	}

	public long getDiskSpaceUsed() {
		return diskSpaceUsed;
	}

	public void setDiskSpaceUsed(long diskSpaceUsed) {
		this.diskSpaceUsed = diskSpaceUsed;
	}

	public long getDiskSpaceFree() {
		return diskSpaceFree;
	}

	public void setDiskSpaceFree(long diskSpaceFree) {
		this.diskSpaceFree = diskSpaceFree;
	}
}
