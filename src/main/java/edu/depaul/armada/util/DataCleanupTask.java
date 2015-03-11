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

import org.apache.log4j.Logger;

import edu.depaul.armada.dao.ContainerDao;
import edu.depaul.armada.dao.ContainerLogDao;

/**
 * Used to cleanup old data
 * 
 * @author ptrzyna
 */
public class DataCleanupTask implements Runnable {
	
	private static final int INTERVAL = 48; 
	
	private Logger log = Logger.getLogger(getClass());
	
	private ContainerLogDao containerLogDao;
	private ContainerDao containerDao;
	
	public void setContainerLogDao(ContainerLogDao containerLogDao) {
		this.containerLogDao = containerLogDao;
	}
	
	/**
	 * @param containerDao the containerDao to set
	 */
	public void setContainerDao(ContainerDao containerDao) {
		this.containerDao = containerDao;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			log.info("-- Cleaning up data older than " + INTERVAL + "h.");
			containerLogDao.deleteOldData(INTERVAL);
			containerDao.deleteOldData(INTERVAL);
		}
		catch(Exception e) {
			log.warn("-- Old data cleanup failed!");
		}
	}

}
