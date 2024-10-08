package com.juvarya.kovela.customer.populator;
import org.springframework.stereotype.Component;

import com.juvarya.kovela.customer.dto.JTDesignationDTO;
import com.juvarya.kovela.customer.model.JTDesignationModel;
import com.juvarya.kovela.utils.converter.Populator;

@Component("jtDesignationPopulator")
public class JTDesignationPopulator implements Populator<JTDesignationModel, JTDesignationDTO>{

	@Override
	public void populate(JTDesignationModel source, JTDesignationDTO target) {
		target.setId(source.getId());
		target.setName(source.getName());
		target.setCode(source.getCode());
	}

}