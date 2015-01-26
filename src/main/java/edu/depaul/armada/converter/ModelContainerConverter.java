/**
 * 
 */
package edu.depaul.armada.converter;


/**
 * @author ptrzyna, john davidson
 *
 */
public class ModelContainerConverter implements Converter<edu.depaul.armada.model.Container, edu.depaul.armada.domain.Container> {

	@Override
	public edu.depaul.armada.domain.Container convert(edu.depaul.armada.model.Container model) {
		edu.depaul.armada.domain.Container domain = new edu.depaul.armada.domain.Container();
		
		domain.setId(model.getId());
		domain.setName(model.getName());
		domain.setDockerId(model.getDockerId());
		domain.setCAdvisorURL(model.getCAdvisorURL());
		domain.setMemLimit(model.getMemLimit());
		domain.setCpuLimit(model.getCpuLimit());
		domain.setFilesystemCapacity(model.getFilesystemCapacity());
		
		return domain;
	}

	
	
}
