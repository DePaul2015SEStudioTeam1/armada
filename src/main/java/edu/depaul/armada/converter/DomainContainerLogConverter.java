/**
 * 
 */
package edu.depaul.armada.converter;

import edu.depaul.armada.domain.Container;


/**
 * @author jplante
 *
 */
public class DomainContainerLogConverter implements Converter<edu.depaul.armada.domain.ContainerLog, edu.depaul.armada.model.ContainerLog> {

	@Override
	public edu.depaul.armada.model.ContainerLog convert(edu.depaul.armada.domain.ContainerLog domain) {
		
		edu.depaul.armada.model.ContainerLog model = new edu.depaul.armada.model.ContainerLog();
		
		model.setId(domain.getId());
		model.setTimestamp(domain.getTimestamp());
		model.setMemUsage(domain.getMemUsage());
		model.setTotalCpuUsage(domain.getTotalCpuUsage());
		model.setFilesystemUsage(domain.getFilesystemUsage());
		model.setMemTotal(domain.getMemTotal());
		model.setTotalCpu(domain.getTotalCpu());
		model.setTotalFilesystem(domain.getTotalFilesystem());
		
		return model;
	}


}
