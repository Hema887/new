package com.juvarya.kovela.customer.service;

import com.juvarya.kovela.customer.model.JTCustomerLastLoginModel;
import com.juvarya.kovela.customer.model.User;

public interface JTCustomerLastLoginService {
	JTCustomerLastLoginModel save(JTCustomerLastLoginModel customerLastLoginModel);

	JTCustomerLastLoginModel findByJtCustomer(User user);
}
