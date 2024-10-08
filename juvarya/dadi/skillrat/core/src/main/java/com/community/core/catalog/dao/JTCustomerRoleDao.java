package com.community.core.catalog.dao;

import com.community.core.catalog.domain.JTCustomerRole;

public interface JTCustomerRoleDao {
	JTCustomerRole save(JTCustomerRole customerRole);

	JTCustomerRole getCustomerRoles(Long customerId);
}
