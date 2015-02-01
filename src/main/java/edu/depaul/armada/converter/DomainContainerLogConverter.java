/**
 * 
 */
package edu.depaul.armada.converter;


/**
 * @author jplante
 *
 */
public class DomainContainerLogConverter implements Converter<edu.depaul.armada.domain.ContainerLog, edu.depaul.armada.model.ContainerLog> {

	@Override
	public edu.depaul.armada.model.ContainerLog convert(edu.depaul.armada.domain.ContainerLog domain) {
		
		edu.depaul.armada.model.ContainerLog model = new edu.depaul.armada.model.ContainerLog();
		
		model.setId(domain.getId());
		model.setContainerId(domain.getContainerId());
		model.setTimestamp(domain.getTimestamp());
		model.setMemUsage(domain.getMemUsage());
		model.setTotalCpuUsage(domain.getTotalCpuUsage());
		model.setFilesystemUsage(domain.getFilesystemUsage());
		
		return model;
	}


}
