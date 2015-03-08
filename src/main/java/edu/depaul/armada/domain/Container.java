package edu.depaul.armada.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * An object which holds data about a Linux containers that have been deployed by the application.
 * @author ptrzyna, john davidson, jplante
 *
 */
public class Container {
	private long id;
	private String name;
	private String containerUniqueId;
	private String cAdvisorURL;
	private List<ContainerLog> logs = new ArrayList<ContainerLog>();

	/**
	 * Returns the long value stored in the field id.
	 * @return long
	 */
	public long getId() {
		return id;
	}

	/**
	 * Accepts a long and uses it to set the current Conainter object’s id field.
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
}
