package com.community.core.catalog.dao;

import java.util.List;

import com.community.core.catalog.domain.JTCustomer;

public interface JTCustomerDao {
	JTCustomer save(JTCustomer customer);
	JTCustomer findCustomer(Long id);
	List<JTCustomer> getCustomers(int pageNo, int pageSize);
	Long customerCount();
	List<JTCustomer> getTrainers(Boolean popular, int pageNo, int pageSize);
	JTCustomer findByTrainer(Long trainerId);
	JTCustomer findById(Long id);
	JTCustomer findByPrimaryContact(String contact);
	JTCustomer findByEmail(String email);
	
}
