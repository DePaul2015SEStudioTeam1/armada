/**
 * 
 */
package edu.depaul.armada.converter;


/**
 * @author jplante
 *
 */
public class ModelContainerLogConverter implements Converter<edu.depaul.armada.model.ContainerLog, edu.depaul.armada.domain.ContainerLog> {
	
	@Override
	public edu.depaul.armada.domain.ContainerLog convert(edu.depaul.armada.model.ContainerLog model) {
		
		edu.depaul.armada.domain.ContainerLog domain = new edu.depaul.armada.domain.ContainerLog();
		
		domain.setId(model.getId());
		domain.setContainerId(model.getContainerId());
		domain.setTimestamp(model.getTimestamp());
		domain.setMemUsage(model.getMemUsage());
		domain.setTotalCpuUsage(model.getTotalCpuUsage());
		domain.setFilesystemUsage(model.getFilesystemUsage());
		
		return domain;
	}

}