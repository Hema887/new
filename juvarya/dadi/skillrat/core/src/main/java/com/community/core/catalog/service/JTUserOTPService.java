package com.community.core.catalog.service;

import com.community.core.catalog.domain.JTCustomer;
import com.community.core.catalog.domain.JTUserOTP;

public interface JTUserOTPService {
	public JTUserOTP save(JTUserOTP jtUserOTP);
	public JTUserOTP findById(Long id);
	public JTUserOTP findByCustomer(JTCustomer customer);
	public JTUserOTP delete(Long jtUserOTPId);
	public JTUserOTP findByEmail(String email);
	public JTUserOTP findByPrimaryContactAndOtpType(String number, String type);
}
