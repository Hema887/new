package com.juvarya.kovela.customer.populator;

import org.springframework.stereotype.Component;

import com.juvarya.kovela.customer.dto.JTZodiacSignDTO;
import com.juvarya.kovela.customer.model.JTZodiacSignModel;
import com.juvarya.kovela.utils.converter.Populator;

@Component("jtZodiacSignPopulator")
public class JTZodiacSignPopulator implements Populator<JTZodiacSignModel,JTZodiacSignDTO>{

	@Override
	public void populate(JTZodiacSignModel source, JTZodiacSignDTO target) {
		target.setId(source.getId());		
		target.setZodiacSign(source.getZodiacSign());
	}

}
