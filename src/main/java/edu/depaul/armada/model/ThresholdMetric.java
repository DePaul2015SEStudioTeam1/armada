/*
 * The MIT License (MIT)
 * 
 * Copyright (c) <year> <copyright holders> 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package edu.depaul.armada.model;

/**
 * An object which holds user-specified thresholds.
 * @author ptrzyna
 *
 */
public class ThresholdMetric {

	private int period = 0;	// hour for which we have counts
	private int ok = 0;
	private int warn = 0;
	private int error = 0;
	
	/**
	 * Accepts an int and uses it to set the ThresholdMetric object’s period field.
	 * @param period int
	 */
	public void setPeriod(int period) {
		this.period = period;
	}
	
	/**
	 * Returns the int value stored in the field period.
	 * @return int period
	 */
	public int getPeriod() {
		return period;
	}
	
	/**
	 * Accepts an int and uses it to set the ThresholdMetric object’s ok field.
	 * @param ok int
	 */
	public void setOk(int ok) {
		this.ok = ok;
	}
	
	/**
	 * Returns the int value stored in the field ok.
	 * @return int ok
	 */
	public int getOk() {
		return ok;
	}
	
	/**
	 * Accepts an int and uses it to set the ThresholdMetric object’s warn field.
	 * @param warn int
	 */
	public void setWarn(int warn) {
		this.warn = warn;
	}
	
	/**
	 * Returns the int value stored in the field warn.
	 * @return int warn
	 */
	public int getWarn() {
		return warn;
	}
	
	/**
	 * Accepts an int and uses it to set the ThresholdMetric object’s error field.
	 * @param error int
	 */
	public void setError(int error) {
		this.error = error;
	}
	
	/**
	 * Returns the int value stored in the field error.
	 * @return int error
	 */
	public int getError() {
		return error;
	}
	
}
