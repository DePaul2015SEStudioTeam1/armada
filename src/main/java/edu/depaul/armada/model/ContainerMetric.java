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
