package com.community.core.catalog.domain;

import java.util.Date;

public interface JTUserOTP {
	Long getId();
	void setId(Long id);
	String getOtpType();
	void setOtpType(String otpType);
	String getChannel();
	void setChannel(String channel);
	String getEmailAddress();
	void setEmailAddress(String emailAddress);
	JTCustomer getJtCustomer();
	void setJtCustomer(JTCustomer jtCustomer);
	String getOtp();
	void setOtp(String otp);
	Date getCreationTime();
	void setCreationTime(Date creationTime);
	public String getPrimaryContact();
	public void setPrimaryContact(String primaryContact);
}
