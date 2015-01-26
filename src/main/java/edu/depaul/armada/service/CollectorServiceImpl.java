/**
 * 
 */
package edu.depaul.armada.service;

import org.apache.log4j.Logger;

import edu.depaul.armada.model.Container;

/**
 * Concrete implementation of the OperationsService
 * 
 * @author John Plante
 */
public class CollectorServiceImpl implements CollectorService<Container> {
    
    private OperationsService<Container> operationsService;
    final Logger logger = Logger.getLogger(CollectorServiceImpl.class);
    
    public void setOperationsService(OperationsService<Container> operationsService){
        this.operationsService = operationsService;
    }

	/* (non-Javadoc)
	 * @see edu.depaul.service.OperationsService#store(edu.depaul.model.Container)
	 */
	public void store(Container container) {
		logger.debug("Container received. [Container: " + container.getId() + " Agent: " + container.getDockerId() + "]");
		
		try {
			operationsService.store(container);
		}
		catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
