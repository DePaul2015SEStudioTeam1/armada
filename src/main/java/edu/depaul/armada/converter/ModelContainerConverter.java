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
		
		domain.setName(model.getName());
		domain.setContainerUniqueId(model.getContainerUniqueId());
		domain.setCAdvisorURL(model.getCAdvisorURL());
		
		return domain;
	}

	
	
}
