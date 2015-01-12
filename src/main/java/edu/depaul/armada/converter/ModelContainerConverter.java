/**
 * 
 */
package edu.depaul.armada.converter;


/**
 * @author ptrzyna
 *
 */
public class ModelContainerConverter implements Converter<edu.depaul.operations.model.Container, edu.depaul.operations.domain.Container> {

	@Override
	public edu.depaul.operations.domain.Container convert(edu.depaul.operations.model.Container model) {
		edu.depaul.operations.domain.Container domain = new edu.depaul.operations.domain.Container();
		
		domain.setId(model.getId());
		domain.setAgentId(model.getAgentId());
		
		domain.setCpuCount(model.getCpuCount());
		domain.setCpuModel(model.getCpuModel());
		domain.setCpuVendor(model.getCpuVendor());
		
		domain.setMemTotal(model.getMemTotal());
		domain.setMemFree(model.getMemFree());
		domain.setMemUsed(model.getMemUsed());
		
		domain.setOsDescription(model.getOsDescription());
		domain.setOsDataModel(model.getOsDataModel());
		domain.setOsName(model.getOsName());
		
		domain.setPrimaryIpAddress(model.getPrimaryIpAddress());
		domain.setPrimaryMacAddress(model.getPrimaryMacAddress());
		
		domain.setHostName(model.getHostName());
		
		domain.setDiskSpaceTotal(model.getDiskSpaceTotal());
		domain.setDiskSpaceFree(model.getDiskSpaceFree());
		domain.setDiskSpaceUsed(model.getDiskSpaceUsed());
		
		return domain;
	}

	
	
}
