/**
 * 
 */
package edu.depaul.armada.model;

/**
 * An object which holds specific data for specific metrics about Linux containers in the application.
 * @author ptrzyna
 */
public class Metric {

	private int hour;
	private int value;
	
	/**
	 * Accepts an int and uses it to set the Metric object’s hour field.
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
	 * Accepts an int and uses it to set the Metric object’s value field.
	 * @param value int
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
	/**
	 * Returns the int value stored in the field value.
	 * @return int value
	 */
	public int getValue() {
		return value;
	}
}
