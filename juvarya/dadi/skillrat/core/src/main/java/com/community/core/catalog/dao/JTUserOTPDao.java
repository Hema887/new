package com.community.core.catalog.dao;

import com.community.core.catalog.domain.JTCustomer;
import com.community.core.catalog.domain.JTUserOTP;

public interface JTUserOTPDao {
	public JTUserOTP save(JTUserOTP jtUser);
	public JTUserOTP findById(Long id);
	public JTUserOTP delete(Long jtUserOTPId);
	public JTUserOTP findByCustomer(JTCustomer jtCustomer);
	public JTUserOTP findByEmail(String email);
	public JTUserOTP findByPrimaryContactAndOtpType(String number, String type);
}
