package com.community.core.azure.service.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.community.core.azure.service.JTCustomerRoleService;
import com.community.core.catalog.dao.JTCustomerRoleDao;
import com.community.core.catalog.domain.JTCustomerRole;

@Service("jtCustomerRoleService")
public class JTCustomerRoleServiceImpl implements JTCustomerRoleService {

	@Resource(name = "jtCustomerRoleDao")
	private JTCustomerRoleDao customerRoleDao;

	@Override
	@Transactional
	public JTCustomerRole save(JTCustomerRole customerRole) {
		return customerRoleDao.save(customerRole);
	}

	@Override
	public JTCustomerRole getCustomerRoles(Long customeerId) {
		return customerRoleDao.getCustomerRoles(customeerId);
	}
}
