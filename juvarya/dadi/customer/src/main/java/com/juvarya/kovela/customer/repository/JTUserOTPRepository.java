package com.juvarya.kovela.customer.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juvarya.kovela.customer.model.JTUserOTPModel;

@Repository("jtUserOTPRepository")
public interface JTUserOTPRepository extends JpaRepository<JTUserOTPModel, Long> {
	List<JTUserOTPModel> findByEmailAddressAndOtpType(String emailAddress, String otpType, Sort sort);

	List<JTUserOTPModel> findByPrimaryContactAndOtpType(String primaryContact, String otpType);

	JTUserOTPModel findByOtpTypeAndPrimaryContact(String otpType, String primaryContact);
}
