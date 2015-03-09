/**
 * 
 */
package edu.depaul.armada.util;

import org.apache.log4j.Logger;

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
	
	public void setContainerLogDao(ContainerLogDao containerLogDao) {
		this.containerLogDao = containerLogDao;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		log.info("-- Cleaning up data older than " + INTERVAL + "h.");
		containerLogDao.deleteOldData(INTERVAL);
	}

}
