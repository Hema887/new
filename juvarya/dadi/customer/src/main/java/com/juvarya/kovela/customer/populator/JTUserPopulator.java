package com.juvarya.kovela.customer.populator;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.juvarya.kovela.commonservices.dto.JTMediaDTO;
import com.juvarya.kovela.customer.dto.JTPostalCodeDTO;
import com.juvarya.kovela.customer.dto.JTUserDTO;
import com.juvarya.kovela.customer.model.User;
import com.juvarya.kovela.feedservices.JTFeedIntegrationService;
import com.juvarya.kovela.feedservices.impl.JTFeedIntegrationServiceImpl;
import com.juvarya.kovela.security.services.CustomerState;
import com.juvarya.kovela.utils.converter.Populator;

@Component("jtUserPopulator")
public class JTUserPopulator implements Populator<User, JTUserDTO> {

	@Resource(name = "jtPostalCodePopulator")
	private JTPostalCodePopulator codePopulator;

	@Override
	public void populate(User source, JTUserDTO target) {
		target.setId(source.getId());
		target.setEmail(source.getEmail());
		target.setFullName(source.getFullName());
		target.setPrimaryContact(source.getPrimaryContact());
		target.setGothra(source.getGothra());
		if (null != source.getType()) {
			target.setType(source.getType());
		}

		if (null != source.getPostalCode()) {
			JTPostalCodeDTO codeDTO = new JTPostalCodeDTO();
			codePopulator.populate(source.getPostalCode(), codeDTO);
			target.setJtPostalCodeDTO(codeDTO);
		}

		getCustomerPicture(source.getPrimaryContact(), target);
	}

	private void getCustomerPicture(String primaryContact, JTUserDTO target) {
		JTFeedIntegrationService meida = new JTFeedIntegrationServiceImpl();
		JTMediaDTO picture = meida.getCustomerPicture(primaryContact, CustomerState.currentUser.get());
		if (null != picture) {
			target.setPicture(picture.getUrl());
		}
	}

}
