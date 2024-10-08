
package com.juvarya.kovela.customer.populator;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.juvarya.kovela.customer.dto.JTAddressDTO;
import com.juvarya.kovela.customer.dto.JTPostalCodeDTO;
import com.juvarya.kovela.customer.model.JTAddressModel;
import com.juvarya.kovela.utils.converter.Populator;

@Component("jtAddressPopulator")
public class JTAddressPopulator implements Populator<JTAddressModel, JTAddressDTO> {
	@Resource(name = "jtPostalCodePopulator")
	private JTPostalCodePopulator codePopulator;

	@Override
	public void populate(JTAddressModel source, JTAddressDTO target) {
		target.setId(source.getId());
		target.setContactNumber(source.getContactNumber());
		target.setLine1(source.getLine1());
		target.setLine2(source.getLine2());
		target.setLine3(source.getLine3());
		target.setLocality(source.getLocality());
		target.setDefaultAddress(source.getDefaultAddress());

		JTPostalCodeDTO codeDTO = new JTPostalCodeDTO();
		if (null != source.getPostalCode()) {
			codePopulator.populate(source.getPostalCode(), codeDTO);
			target.setPostalCodeDTO(codeDTO);
		}
	}

}
