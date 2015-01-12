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
 * @author Paul A. Trzyna
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
		edu.depaul.armada.domain.Container old = containerDao.findWithAgentId(container.getAgentId());
		edu.depaul.armada.domain.Container domain = modelConverter.convert(container);
		
		if(old != null) {
			merge(old, domain);
			containerDao.store(old);
			return;
		}
		
		containerDao.store(domain);
	}
	
	private void merge(edu.depaul.armada.domain.Container old, edu.depaul.armada.domain.Container domain) {
		old.setAgentId(domain.getAgentId());
		
		old.setCpuCount(domain.getCpuCount());
		old.setCpuModel(domain.getCpuModel());
		old.setCpuVendor(domain.getCpuVendor());
		
		old.setMemTotal(domain.getMemTotal());
		old.setMemFree(domain.getMemFree());
		old.setMemUsed(domain.getMemUsed());
		
		old.setOsDescription(domain.getOsDescription());
		old.setOsDataModel(domain.getOsDataModel());
		old.setOsName(domain.getOsName());
		
		old.setPrimaryIpAddress(domain.getPrimaryIpAddress());
		old.setPrimaryMacAddress(domain.getPrimaryMacAddress());
		
		old.setHostName(domain.getHostName());
		
		old.setDiskSpaceTotal(domain.getDiskSpaceTotal());
		old.setDiskSpaceFree(domain.getDiskSpaceFree());
		old.setDiskSpaceUsed(domain.getDiskSpaceUsed());
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.operations.service.OperationsService#getAll()
	 */
	@Override
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
