/*
 * The MIT License (MIT)
 * Copyright (c) <year> <copyright holders> 
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
package edu.depaul.armada.dao;

import java.util.List;

import edu.depaul.armada.domain.Container;
import edu.depaul.armada.model.DashboardContainer;


/**
 * Used for CRUD operations associated with Container instances
 * 
 * @author ptrzyna
 */
public interface ContainerDao {

	/**
	 * Accepts a Container object, and uses Hibernate's saveOrUpdate method to save the
	 * most recent container data in the database.
	 * @param container	Container instance we want to store
	 */
	void store(Container container);
	
	/**
	 * Returns a List<DashboardContainer> object.
	 * @return List<DashboardContainer>
	 */
	List<DashboardContainer> getAllDashboardContainers();
	
	/**
	 * Accepts a long and an int, returns a List<DashboardContainer> object.
	 * @param id long
	 * @param count int
	 * @return List<DashboardContainer>
	 */
	List<DashboardContainer> getDashboardContainers(long id, int count);

	/**
	 * Returns a List<Container> object.
	 * @return List<Container>
	 */
	List<Container> getAll();
	
	/**
	 * Accepts a long and an int, and returns a List<Container> object of the Container data in the database.
	 * @param id long
	 * @param count int
	 * @return List<Container>
	 */
	List<Container> get(long id, int count);
	
	/**
	 * Returns a single Container object with a containerId field matching the one specified
	 * as a parameter.
	 * @param containerId	id of the container we want to retrieve
	 * @return container instance matching the id
	 */
	Container findWithContainerId(long containerId);

	/**
	 * Returns a single Container object with a containerUniqueId field matching the one specified
	 * as a parameter.
	 * @param containerUniqueId	id used to find the container
	 * @return Container matching the id
	 */
	Container findWithContainerUniqueId(String containerUniqueId);
	
	/**
	 * Deletes data older than given interval in hours specified in the parameter it is passed.
	 * @param interval	interval in hours
	 */
	void deleteOldData(int interval);
}
