package com.juvarya.kovela.customer.populator;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.juvarya.kovela.customer.dto.JTDistrictDTO;
import com.juvarya.kovela.customer.dto.JTRegionDTO;
import com.juvarya.kovela.customer.model.JTDistrictModel;
import com.juvarya.kovela.utils.converter.Populator;

@Component("jtDistrictPopulator")
public class JTDistrictPopulator implements Populator<JTDistrictModel, JTDistrictDTO>{
	
	@Resource(name = "jtRegionPopulator")
	private JTRegionPopulator regionPopulator;

	@Override
	public void populate(JTDistrictModel source, JTDistrictDTO target) {
		target.setId(source.getId());
		target.setName(source.getName());
		target.setIsoCode(source.getIsoCode());
		
		if(null != source.getRegion()) {
			JTRegionDTO dto = new JTRegionDTO();
			regionPopulator.populate(source.getRegion(), dto);
			target.setRegionDTO(dto);
		}
		
		
	}

}
