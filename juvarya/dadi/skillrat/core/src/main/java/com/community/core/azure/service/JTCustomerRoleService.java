package com.community.core.azure.service;

import com.community.core.catalog.domain.JTCustomerRole;

public interface JTCustomerRoleService {

	JTCustomerRole save(JTCustomerRole customerRole);

	JTCustomerRole getCustomerRoles(Long customeerId);
}
