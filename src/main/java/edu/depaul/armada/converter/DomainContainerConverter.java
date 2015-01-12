/**
 * 
 */
package edu.depaul.armada.converter;


/**
 * @author ptrzyna
 *
 */
public class DomainContainerConverter implements Converter<edu.depaul.armada.domain.Container, edu.depaul.armada.model.Container> {

	@Override
	public edu.depaul.armada.model.Container convert(edu.depaul.armada.domain.Container domain) {
		edu.depaul.armada.model.Container model = new edu.depaul.armada.model.Container();
		
		model.setId(domain.getId());
		model.setAgentId(domain.getAgentId());
		
		model.setCpuCount(domain.getCpuCount());
		model.setCpuModel(domain.getCpuModel());
		model.setCpuVendor(domain.getCpuVendor());
		
		model.setMemTotal(domain.getMemTotal());
		model.setMemFree(domain.getMemFree());
		model.setMemUsed(domain.getMemUsed());
		
		model.setOsDescription(domain.getOsDescription());
		model.setOsDataModel(domain.getOsDataModel());
		model.setOsName(domain.getOsName());
		
		model.setPrimaryIpAddress(domain.getPrimaryIpAddress());
		model.setPrimaryMacAddress(domain.getPrimaryMacAddress());
		
		model.setHostName(domain.getHostName());
		
		model.setDiskSpaceTotal(domain.getDiskSpaceTotal());
		model.setDiskSpaceFree(domain.getDiskSpaceFree());
		model.setDiskSpaceUsed(domain.getDiskSpaceUsed());
		
		return model;
	}

	
	
}
