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
import java.util.ArrayList;
import java.util.List;

/**
 * An object which holds data about Linux containers that have been deployed by the application.
 * @author ptrzyna, john davidson, jplante
 *
 */
public class Container {
	private long id;
	private String name;
	private String containerUniqueId;
	private String cAdvisorURL;
	private Timestamp timestamp;
	private List<ContainerLog> logs = new ArrayList<ContainerLog>();

	/**
	 * Returns the long value stored in the field id.
	 * @return long
	 */
	public long getId() {
		return id;
	}

	/**
	 * Accepts a long and uses it to set the current Container object’s id field.
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns the String value stored in the field name.
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Accepts a String and uses it to set the current Container object’s name field.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the String value stored in the field containerUniqueId.
	 * @return String
	 */
	public String getContainerUniqueId() {
		return containerUniqueId;
	}

	/**
	 * Accepts a String and uses it to set the current Container object’s 
	 * containerUniqueId field.
	 * @param containerUniqueId
	 */
	public void setContainerUniqueId(String containerUniqueId) {
		this.containerUniqueId = containerUniqueId;
	}

	/**
	 * Returns the String value stored in the field cAdvisorURL.
	 * @return String
	 */
	public String getCAdvisorURL() {
		return cAdvisorURL;
	}

	/**
	 * Accepts a String and uses it to set the current Container object’s cAdvisorURL field.
	 * @param cAdvisorURL
	 */
	public void setCAdvisorURL(String cAdvisorURL) {
		this.cAdvisorURL = cAdvisorURL;
	}

	/**
	 * Returns an object of type List<ContainerLog> stored in the field logs.
	 * @return List<ContainerLog>
	 */
	public List<ContainerLog> getLogs(){
		return logs;
	}

	/**
	 * Accepts an object of type List<ContainerLog> and uses it to set the current Container 
	 * object’s logs field.
	 * @param logs
	 */
	public void setLogs(List<ContainerLog> logs) {
		this.logs = logs;
	}
	
	/**
	 * Accepts an object of type ContainerLog and adds it to the List<ContainerLog> object 
	 * in the current Container object’s logs field.
	 * @param log
	 */
	public void addLog(ContainerLog log) {
		log.setContainer(this);
		this.logs.add(log);
	}
	
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	/**
	 * @return the timestamp
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}
}
