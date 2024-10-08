package com.juvarya.kovela.customer.populator;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.juvarya.kovela.customer.dto.JTStoreDTO;
import com.juvarya.kovela.customer.model.JTStoreModel;
import com.juvarya.kovela.utils.converter.Populator;

@Component("jtStorePopulator")
public class JTStorePopulator implements Populator<JTStoreModel, JTStoreDTO> {

	@Resource(name = "jtAddressPopulator")
	private JTAddressPopulator addressPopulator;

	@Override
	public void populate(JTStoreModel source, JTStoreDTO target) {
		target.setId(source.getId());
		target.setContactName(source.getContactName());
		target.setContactNumber(source.getContactNumber());
		target.setName(source.getName());

		target.setActive(source.getActive());
		target.setProfileId(source.getProfile());
	}

}
