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
package edu.depaul.armada.util;

import java.sql.Timestamp;
import java.util.UUID;

import org.apache.commons.lang3.RandomUtils;

import edu.depaul.armada.domain.Container;
import edu.depaul.armada.domain.ContainerLog;
import edu.depaul.armada.domain.Preference;

/**
 * Test utility that can be used to easily set up test objects
 * 
 * @author ptrzyna
 */
public class TestUtil {

	public static Container newContainer() {
		Container container = new Container();
		container.setCAdvisorURL("http://localhost:8080/cAdvisor");
		container.setContainerUniqueId(UUID.randomUUID().toString());
		container.setName("test-name");
		container.setTimestamp(new Timestamp(System.currentTimeMillis()));
		return container;
	}
	
	public static ContainerLog newContainerLog() {
		ContainerLog log = new ContainerLog();
		log.setTimestamp(new Timestamp(0));
		log.setCpuUsed(RandomUtils.nextLong(0, 100));
		log.setCpuTotal(100);
		log.setMemUsed(RandomUtils.nextLong(0, 100));
		log.setMemTotal(100);
		log.setDiskUsed(RandomUtils.nextLong(0, 100));
		log.setDiskTotal(100);
		return log;
	}
	
	public static Preference newPreference() {
		Preference preference = new Preference();
		preference.setName("cpu");
		preference.setValue(90);
		return preference;
	}
	
}
