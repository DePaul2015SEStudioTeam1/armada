/**
 * 
 */
package edu.depaul.armada.model;

/**
 * An object which holds specific metrics about Linux containers that have been deployed by the application.
 * @author ptrzyna
 */
public class ContainerMetric {

	private int hour;
	private long cpu;
	private long mem;
	private long disk;
	
	/**
	 * Accepts an int and uses it to set the ContainerMetric object’s hour field.
	 * @param hour int
	 */
	public void setHour(int hour) {
		this.hour = hour;
	}
	
	/**
	 * Returns the int value stored in the field hour.
	 * @return int hour
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * Returns the long value stored in the field cpu.
	 * @return long cpu
	 */
	public long getCpu() {
		return cpu;
	}
	
	/**
	 * Accepts a long and uses it to set the current ConainterMetric object’s cpu field.
	 * @param long cpu
	 */
	public void setCpu(long cpu) {
		this.cpu = cpu;
	}

	/**
	 * Returns the long value stored in the field mem.
	 * @return long mem
	 */
	public long getMem() {
		return mem;
	}

	/**
	 * Accepts a long and uses it to set the current ConainterMetric object’s mem field.
	 * @param long mem
	 */
	public void setMem(long mem) {
		this.mem = mem;
	}

	/**
	 * Returns the long value stored in the field disk.
	 * @return long disk
	 */
	public long getDisk() {
		return disk;
	}

	/**
	 * Accepts a long and uses it to set the current ConainterMetric object’s disk field.
	 * @param long disk
	 */
	public void setDisk(long disk) {
		this.disk = disk;
	}
	

}
