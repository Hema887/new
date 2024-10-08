package com.juvarya.kovela.customer.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "JT_USER_OTP",
indexes = {@Index(name = "idx_userotpid",  columnList="id", unique = true)})
public class JTUserOTPModel {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "OTP_TYPE")
	private String otpType;

	@Column(name = "CHANNEL")
	private String channel;

	@Column(name = "EMAIL_ADDRESS")
	private String emailAddress;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, optional = true)
	private User user;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}

}
