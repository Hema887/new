package com.juvarya.kovela.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juvarya.kovela.customer.model.JTCustomerLastLoginModel;
import com.juvarya.kovela.customer.model.User;

@Repository("jtCustomerLastLoginRepository")
public interface JTCustomerLastLoginRepository extends JpaRepository<JTCustomerLastLoginModel, Long> {

	JTCustomerLastLoginModel findByJtCustomer(User user);
}
