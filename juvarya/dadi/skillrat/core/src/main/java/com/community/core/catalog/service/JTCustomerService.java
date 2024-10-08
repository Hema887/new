package com.community.core.catalog.service;

import org.broadleafcommerce.profile.core.domain.Customer;

import com.community.core.catalog.domain.JTCustomer;

public interface JTCustomerService {
	JTCustomer save(JTCustomer customer);
	Customer validateCustomer(JTCustomer jtCustomer);
	JTCustomer findById(Long id);
	JTCustomer findByPrimaryContact(String contact);
	JTCustomer findByEmail(String email);
}
