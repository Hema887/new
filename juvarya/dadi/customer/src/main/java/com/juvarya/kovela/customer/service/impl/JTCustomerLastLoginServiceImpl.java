package com.juvarya.kovela.customer.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juvarya.kovela.customer.model.JTCustomerLastLoginModel;
import com.juvarya.kovela.customer.model.User;
import com.juvarya.kovela.customer.repository.JTCustomerLastLoginRepository;
import com.juvarya.kovela.customer.service.JTCustomerLastLoginService;

@Service("jtCustomerLastLoginService")
public class JTCustomerLastLoginServiceImpl implements JTCustomerLastLoginService {

	@Resource(name = "jtCustomerLastLoginRepository")
	private JTCustomerLastLoginRepository customerLastLoginRepository;

	@Override
	@Transactional
	public JTCustomerLastLoginModel save(JTCustomerLastLoginModel customerLastLoginModel) {
		return customerLastLoginRepository.save(customerLastLoginModel);
	}

	@Override
	public JTCustomerLastLoginModel findByJtCustomer(User user) {
		return customerLastLoginRepository.findByJtCustomer(user);
	}

}
