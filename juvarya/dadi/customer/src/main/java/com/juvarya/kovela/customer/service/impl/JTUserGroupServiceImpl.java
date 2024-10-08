package com.juvarya.kovela.customer.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.juvarya.kovela.customer.model.JTUserGroupModel;
import com.juvarya.kovela.customer.repository.JTUserGroupRepository;
import com.juvarya.kovela.customer.service.JTUserGroupService;

@Service("jtUserGroupService")
public class JTUserGroupServiceImpl implements JTUserGroupService{
	
	@Resource(name = "jtUserGroupRepository")
	private JTUserGroupRepository jtUserGroupRepository;

	@Override
	public JTUserGroupModel saveUserGroup(JTUserGroupModel groupModel) {
		return jtUserGroupRepository.save(groupModel);
	}

}
