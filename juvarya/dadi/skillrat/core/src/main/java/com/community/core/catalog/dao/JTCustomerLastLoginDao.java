package com.community.core.catalog.dao;

import com.community.core.catalog.domain.JTCustomerLastLogin;

public interface JTCustomerLastLoginDao {
	JTCustomerLastLogin save(JTCustomerLastLogin customerLastLogin);

	JTCustomerLastLogin findByJtCustomer(Long customerId);
}
