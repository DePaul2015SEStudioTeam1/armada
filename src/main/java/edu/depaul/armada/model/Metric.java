/**
 * 
 */
package edu.depaul.armada.model;

/**
 * @author ptrzyna
 */
public class Metric {

	private int hour;
	private int value;
	
	/**
	 * @param hour the hour to set
	 */
	public void setHour(int hour) {
		this.hour = hour;
	}
	
	/**
	 * @return the hour
	 */
	public int getHour() {
		return hour;
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
}
