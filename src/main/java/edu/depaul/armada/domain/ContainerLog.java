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
package edu.depaul.armada.domain;

import java.sql.Timestamp;

/**
 * An object which holds log data for a specific Container object.
 * @author jplante jdavidson
 */
public class ContainerLog implements Comparable<ContainerLog> {

	private long id;
	private Container container;
	private Timestamp timestamp;
	private long cpuUsed;
	private long cpuTotal;
	private long diskUsed;
	private long diskTotal;
	private long memUsed;
	private long memTotal;

	/**
	 * Returns the long value stored in the field id.
	 * @return long
	 */
	public long getId() {
		return id;
	}

	/**
	 * Accepts a long and uses it to set the current ConainterLog object’s id field.
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns the Container object stored in the field container.
	 * @return Container
	 */
	public Container getContainer() {
		return container;
	}

	/**
	 * Accepts a Container object and uses it to set the current 
	 * ContainerLog object’s container field.
	 * @param container
	 */
	public void setContainer(Container container) {
		this.container = container;
	}

	/**
	 * Returns the Timestamp object stored in the field timestamp.
	 * @return Timestamp
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}

	/**
	 * Accepts a Timestamp object and uses it to set the current 
	 * ContainerLog object’s timestamp field.
	 * @param timestamp
	 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Returns the long value stored in the field cpuUsed.
	 * @return long
	 */
	public long getCpuUsed() {
		return cpuUsed;
	}

	/**
	 * Accepts a long value and uses it to set the current ContainerLog 
	 * object’s cpuUsed field.
	 * @param cpuUsed
	 */
	public void setCpuUsed(long cpuUsed) {
		this.cpuUsed = cpuUsed;
	}

	/**
	 * Returns the long value stored in the field cpuTotal.
	 * @return long
	 */
	public long getCpuTotal() {
		return cpuTotal;
	}

	/**
	 * Accepts a long value and uses it to set the current 
	 * ContainerLog object’s cpuTotal field.
	 * @param cpuTotal
	 */
	public void setCpuTotal(long cpuTotal) {
		this.cpuTotal = cpuTotal;
	}

	/**
	 * Returns the long value stored in the field memUsed.
	 * @return long
	 */
	public long getMemUsed() {
		return memUsed;
	}

	/**
	 * Accepts a long value and uses it to set the current ContainerLog 
	 * object’s memUsed field.
	 * @param memUsed
	 */
	public void setMemUsed(long memUsed) {
		this.memUsed = memUsed;
	}

	/**
	 * Returns the long value stored in the field memTotal.
	 * @return long
	 */
	public long getMemTotal() {
		return memTotal;
	}

	/**
	 * Accepts a long value and uses it to set the current 
	 * ContainerLog object’s memTotal field.
	 * @param memTotal
	 */
	public void setMemTotal(long memTotal) {
		this.memTotal = memTotal;
	}

	/**
	 * Returns the long value stored in the field diskUsed.
	 * @return long
	 */
	public long getDiskUsed() {
		return diskUsed;
	}

	/**
	 * Accepts a long value and uses it to set the current 
	 * ContainerLog object’s diskUsed field.
	 * @param diskUsed
	 */
	public void setDiskUsed(long diskUsed) {
		this.diskUsed = diskUsed;
	}

	/**
	 * Returns the long value stored in the field diskTotal.
	 * @return long
	 */
	public long getDiskTotal() {
		return diskTotal;
	}

	/**
	 * Accepts a long value and uses it to set the current 
	 * ContainerLog object’s diskTotal field.
	 * @param diskTotal
	 */
	public void setDiskTotal(long diskTotal) {
		this.diskTotal = diskTotal;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	/**
	 * Accepts a ContainerLog object and compares it to the current
	 * ContainerLog object’s timestamp using the java.sql.timestamp.compareTo(Timestamp) method.
	 * @param o
	 * @return int 
	 */
	@Override
	public int compareTo(ContainerLog o) {
		return this.timestamp.compareTo(o.timestamp);
	}	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass() + " {id:" + id + ", timestamp:" + timestamp + "}";
	}

}
