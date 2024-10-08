package com.juvarya.kovela.customer.populator;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.juvarya.kovela.customer.dto.JTCountryDTO;
import com.juvarya.kovela.customer.dto.JTRegionDTO;
import com.juvarya.kovela.customer.model.JTRegionModel;
import com.juvarya.kovela.utils.converter.Populator;

@Component("jtRegionPopulator")
public class JTRegionPopulator implements Populator<JTRegionModel, JTRegionDTO> {

	@Resource(name = "jtCountryPopulator")
	private JTCountryPopulator countryPopulator;

	@Override
	public void populate(JTRegionModel source, JTRegionDTO target) {
		target.setId(source.getId());
		target.setIsoCode(source.getIsoCode());
		target.setName(source.getName());

		JTCountryDTO countryDTO = new JTCountryDTO();
		if (null != source.getCountry())
			countryPopulator.populate(source.getCountry(), countryDTO);
			target.setCountry(countryDTO);
	}

}
