/**
 * 
 */
package edu.depaul.armada.converter;


/**
 * @author ptrzyna, john davidson
 *
 */
public class DomainContainerConverter implements Converter<edu.depaul.armada.domain.Container, edu.depaul.armada.model.Container> {

	@Override
	public edu.depaul.armada.model.Container convert(edu.depaul.armada.domain.Container domain) {
		edu.depaul.armada.model.Container model = new edu.depaul.armada.model.Container();

		model.setName(domain.getName());
		model.setContainerUniqueId(domain.getContainerUniqueId());
		model.setcAdvisorURL(domain.getCAdvisorURL());

		return model;
	}

	
	
}
