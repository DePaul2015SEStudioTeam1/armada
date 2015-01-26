/**
 * 
 */
package edu.depaul.armada.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import edu.depaul.armada.converter.DomainContainerConverter;
import edu.depaul.armada.converter.ModelContainerConverter;
import edu.depaul.armada.dao.ContainerDao;
import edu.depaul.armada.model.Container;

/**
 * Concrete implementation of the OperationsService
 * 
 * @author Paul A. Trzyna, John Davidson
 */
@Transactional
public class OperationsServiceImpl implements OperationsService<Container> {
	
	private ContainerDao<edu.depaul.armada.domain.Container> containerDao;
	private DomainContainerConverter domainConverter;
	private ModelContainerConverter modelConverter;
	
	/**
	 * @param modelConverter the modelConverter to set
	 */
	public void setModelConverter(ModelContainerConverter modelConverter) {
		this.modelConverter = modelConverter;
	}
	
	/**
	 * @param domainConverter the domainConverter to set
	 */
	public void setDomainConverter(DomainContainerConverter domainConverter) {
		this.domainConverter = domainConverter;
	}
	
	/**
	 * @param containerDao the containerDao to set
	 */
	public void setContainerDao(ContainerDao<edu.depaul.armada.domain.Container> containerDao) {
		this.containerDao = containerDao;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.service.OperationsService#store(edu.depaul.armada.model.Container)
	 */
	public void store(Container container) {
		// TODO add versioning of db records to let hibernate do this automatically
		edu.depaul.armada.domain.Container old = containerDao.findWithDockerId(container.getDockerId());


		edu.depaul.armada.domain.Container domain = modelConverter.convert(container);
		
		if(old != null) {
			merge(old, domain);
			containerDao.store(old);
			return;
		}
		
		containerDao.store(domain);
	}
	
	private void merge(edu.depaul.armada.domain.Container old, edu.depaul.armada.domain.Container domain) {
		old.setName(domain.getName());
		old.setDockerId(domain.getDockerId());
		old.setCAdvisorURL(domain.getCAdvisorURL());
		old.setMemLimit(domain.getMemLimit());
		old.setCpuLimit(domain.getCpuLimit());
		old.setFilesystemCapacity(domain.getFilesystemCapacity());
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.operations.service.OperationsService#getAll()
	 */
	@ Override
	public List<Container> getAll() {
		
		List<edu.depaul.armada.domain.Container> domainContainers = containerDao.getAll(); 
		List<Container> modelContainers = new ArrayList<Container>();
		for(edu.depaul.armada.domain.Container domain : domainContainers) {
			modelContainers.add(domainConverter.convert(domain));
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
			modelContainers.add(domainConverter.convert(domain));
		}
		
		return modelContainers; 
	}

}
