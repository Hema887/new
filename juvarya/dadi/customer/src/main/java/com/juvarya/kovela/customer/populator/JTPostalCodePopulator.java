package com.juvarya.kovela.customer.populator;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.juvarya.kovela.customer.dto.JTCityDTO;
import com.juvarya.kovela.customer.dto.JTPostalCodeDTO;
import com.juvarya.kovela.customer.model.JTPostalCodeModel;
import com.juvarya.kovela.utils.converter.Populator;

@Component("jtPostalCodePopulator")
public class JTPostalCodePopulator implements Populator<JTPostalCodeModel, JTPostalCodeDTO> {

	@Resource(name = "jtCityPopulator")
	private JTCityPopulator jtCityPopulator;

	@Override
	public void populate(JTPostalCodeModel source, JTPostalCodeDTO target) {
		target.setId(source.getId());
		target.setCode(source.getCode());
		JTCityDTO cityDTO = new JTCityDTO();
		if (null != source.getCity()) {
			jtCityPopulator.populate(source.getCity(), cityDTO);
			target.setCity(cityDTO);
		}
	}
}
