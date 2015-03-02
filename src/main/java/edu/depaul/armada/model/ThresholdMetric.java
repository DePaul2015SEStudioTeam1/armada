/**
 * 
 */
package edu.depaul.armada.model;

/**
 * @author ptrzyna
 *
 */
public class ThresholdMetric {

	private int period = 0;	// hour for which we have counts
	private int ok = 0;
	private int warn = 0;
	private int error = 0;
	
	/**
	 * @param period the period to set
	 */
	public void setPeriod(int period) {
		this.period = period;
	}
	
	/**
	 * @return the period
	 */
	public int getPeriod() {
		return period;
	}
	
	/**
	 * @param ok the ok to set
	 */
	public void setOk(int ok) {
		this.ok = ok;
	}
	
	/**
	 * @return the ok
	 */
	public int getOk() {
		return ok;
	}
	
	/**
	 * @param warn the warn to set
	 */
	public void setWarn(int warn) {
		this.warn = warn;
	}
	
	/**
	 * @return the warn
	 */
	public int getWarn() {
		return warn;
	}
	
	/**
	 * @param error the error to set
	 */
	public void setError(int error) {
		this.error = error;
	}
	
	/**
	 * @return the error
	 */
	public int getError() {
		return error;
	}
	
}
