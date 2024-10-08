package com.juvarya.kovela.customer.service;

import java.util.List;

import com.juvarya.kovela.customer.model.JTUserOTPModel;

public interface JTUserOTPService {
	JTUserOTPModel save(JTUserOTPModel userOTP);

	JTUserOTPModel findByEmailAddressAndOtpType(String emailAddress, String otpType);

	void deleteOTP(JTUserOTPModel userOtp);

	List<JTUserOTPModel> findByPrimaryContactAndOtpType(String primaryContact, String otpType);

	JTUserOTPModel findByOtpTypeAndPrimaryContact(String otpType, String primaryContact);
}
