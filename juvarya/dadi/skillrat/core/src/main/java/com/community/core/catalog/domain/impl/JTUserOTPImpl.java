package com.community.core.catalog.domain.impl;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.community.core.catalog.domain.JTCustomer;
import com.community.core.catalog.domain.JTUserOTP;

@Entity
@Table(name = "JTUSER_OTP")
public class JTUserOTPImpl implements JTUserOTP {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "OTP_TYPE")
	private String otpType;

	@Column(name = "CHANNEL")
	private String channel;

	@Column(name = "EMAIL_ADDRESS")
	private String emailAddress;

	@ManyToOne(cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, targetEntity = JTCustomerImpl.class, optional = true)
	@JoinColumn(name = "JT_CUSTOMER")
	private JTCustomer jtCustomer;

	@Column(name = "CREATION_TIME")
	private Date creationTime;

	@Column(name = "OTP")
	private String otp;
	
	@Column(name = "PRIMARY_CONTACT")
	private String primaryContact;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOtpType() {
		return otpType;
	}

	public void setOtpType(String otpType) {
		this.otpType = otpType;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public JTCustomer getJtCustomer() {
		return jtCustomer;
	}

	public void setJtCustomer(JTCustomer jtCustomer) {
		this.jtCustomer = jtCustomer;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}
}
