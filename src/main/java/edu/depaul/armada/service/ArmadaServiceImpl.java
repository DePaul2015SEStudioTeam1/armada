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
import edu.depaul.armada.model.Container;
import edu.depaul.armada.model.ContainerLog;

/**
 * Concrete implementation of the OperationsService
 * 
 * @author John Plante
 */
@Transactional
public class ArmadaServiceImpl implements ArmadaService {
    
    private final Logger logger = Logger.getLogger(ArmadaServiceImpl.class);

	private ContainerDao containerDao;
	private ContainerLogDao containerLogDao;
	private DomainContainerConverter domainContainerConverter;
	private ModelContainerConverter modelContainerConverter;
	private DomainContainerLogConverter domainContainerLogConverter;
	private ModelContainerLogConverter modelContainerLogConverter;
	
	public void setContainerDao(ContainerDao containerDao) {
		this.containerDao = containerDao;
	}
	
	public void setContainerLogDao(ContainerLogDao containerLogDao) {
		this.containerLogDao = containerLogDao;
	}
	
	public void setDomainContainerConverter(
			DomainContainerConverter domainContainerConverter) {
		this.domainContainerConverter = domainContainerConverter;
	}
	
	public void setDomainContainerLogConverter(
			DomainContainerLogConverter domainContainerLogConverter) {
		this.domainContainerLogConverter = domainContainerLogConverter;
	}
	
	public void setModelContainerConverter(
			ModelContainerConverter modelContainerConverter) {
		this.modelContainerConverter = modelContainerConverter;
	}
	
	public void setModelContainerLogConverter(
			ModelContainerLogConverter modelContainerLogConverter) {
		this.modelContainerLogConverter = modelContainerLogConverter;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.service.OperationsService#store(edu.depaul.armada.model.Container)
	 */
	public void store(Container container) {
		// TODO add versioning of db records to let hibernate do this automatically
		edu.depaul.armada.domain.Container old = containerDao.findWithContainerId(container.getContainerId());

		edu.depaul.armada.domain.Container domain = modelContainerConverter.convert(container);
		
		if(old != null) {
			merge(old, domain);
			containerDao.store(old);
			return;
		}
		
		containerDao.store(domain);
	}
	
	private void merge(edu.depaul.armada.domain.Container old, edu.depaul.armada.domain.Container domain) {
		old.setName(domain.getName());
		old.setContainerId(domain.getContainerId());
		old.setcAdvisorURL(domain.getcAdvisorURL());
		old.setMemLimit(domain.getMemLimit());
		old.setCpuLimit(domain.getCpuLimit());
		old.setFilesystemCapacity(domain.getFilesystemCapacity());
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.operations.service.OperationsService#getAll()
	 */
	@Override
	public List<Container> getAll() {
		
		List<edu.depaul.armada.domain.Container> domainContainers = containerDao.getAll(); 
		List<Container> modelContainers = new ArrayList<Container>();
		for(edu.depaul.armada.domain.Container domain : domainContainers) {
			modelContainers.add(domainContainerConverter.convert(domain));
		}
		
		return modelContainers; 
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.operations.service.OperationsService#get(long, int)
	 */
	@Override
	public List<Container> get(long id, int count) {
		
		List<edu.depaul.armada.domain.Container> domainContainers = containerDao.get(id, count); 
		List<Container> modelContainers = new ArrayList<Container>();
		for(edu.depaul.armada.domain.Container domain : domainContainers) {
			modelContainers.add(domainContainerConverter.convert(domain));
		}
		
		return modelContainers; 
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.service.OperationsService#store(edu.depaul.armada.model.ContainerLog)
	 */
	@Override
	public void store(ContainerLog containerLog) {
		edu.depaul.armada.domain.ContainerLog domain = modelContainerLogConverter.convert(containerLog);
		containerLogDao.store(domain);
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.service.OperationsService#getAll(java.lang.String)
	 */
	@Override
	public List<ContainerLog> getAll(String containerId) {
		// TODO Auto-generated method stub
		return null;
	}

}
