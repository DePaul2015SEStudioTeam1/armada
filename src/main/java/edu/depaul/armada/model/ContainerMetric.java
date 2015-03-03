/**
 * 
 */
package edu.depaul.armada.model;

/**
 * @author ptrzyna
 */
public class ContainerMetric {

	private int hour;
	private long cpu;
	private long mem;
	private long disk;
	
	public void setHour(int hour) {
		this.hour = hour;
	}
	
	public int getHour() {
		return hour;
	}

	public long getCpu() {
		return cpu;
	}

	public void setCpu(long cpu) {
		this.cpu = cpu;
	}

	public long getMem() {
		return mem;
	}

	public void setMem(long mem) {
		this.mem = mem;
	}

	public long getDisk() {
		return disk;
	}

	public void setDisk(long disk) {
		this.disk = disk;
	}
	

}
