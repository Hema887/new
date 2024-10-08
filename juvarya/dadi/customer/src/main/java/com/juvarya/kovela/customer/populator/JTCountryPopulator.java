package com.juvarya.kovela.customer.populator;

import org.springframework.stereotype.Component;

import com.juvarya.kovela.customer.dto.JTCountryDTO;
import com.juvarya.kovela.customer.model.JTCountryModel;
import com.juvarya.kovela.utils.converter.Populator;

@Component("jtCountryPopulator")
public class JTCountryPopulator implements Populator<JTCountryModel, JTCountryDTO> {

	@Override
	public void populate(JTCountryModel source, JTCountryDTO target) {
		target.setId(source.getId());
		target.setIsoCode(source.getIsoCode());
		target.setName(source.getName());
	}

}
