/**
 * 
 */
package edu.depaul.armada.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import edu.depaul.armada.converter.DomainContainerConverter;
import edu.depaul.armada.converter.DomainContainerLogConverter;
import edu.depaul.armada.converter.ModelContainerConverter;
import edu.depaul.armada.converter.ModelContainerLogConverter;
import edu.depaul.armada.dao.ContainerDao;
import edu.depaul.armada.dao.ContainerLogDao;
import edu.depaul.armada.model.AgentContainer;
import edu.depaul.armada.model.Container;
import edu.depaul.armada.model.ContainerLog;

/**
 * Concrete implementation of the OperationsService
 * 
 * @author John Plante
 */
@Transactional
public class ArmadaServiceImpl implements ArmadaService {

	@Override
	public void send(AgentContainer container) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send(List<AgentContainer> container) {
		// TODO Auto-generated method stub
		
	}
    


}
