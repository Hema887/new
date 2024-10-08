package com.juvarya.kovela.customer.populator;

import org.springframework.stereotype.Component;

import com.juvarya.kovela.customer.dto.JTUserGroupDTO;
import com.juvarya.kovela.customer.model.JTUserGroupModel;
import com.juvarya.kovela.utils.converter.Populator;

@Component("jtUserGroupPopulator")
public class JTUserGroupPopulator implements Populator<JTUserGroupModel, JTUserGroupDTO>{

	@Override
	public void populate(JTUserGroupModel source, JTUserGroupDTO target) {
		target.setId(source.getId());
		target.setProfileId(source.getProfileId());
		target.setCode(source.getCode());
	}

}
