package com.juvarya.kovela.customer.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.juvarya.kovela.customer.model.User;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTUserOTPDTO {
	@XmlElement
	private Long id;

	@NotBlank(message = "otpType is mandatory")
	private String otpType;

	@XmlElement
	private String channel;

	private String emailAddress;

	@XmlElement
	private Long userId;

	@XmlElement
	private Date creationTime;

	@XmlElement
	private String otp;

	@XmlElement
	private User user;
	
	@NotBlank(message = "primary contact is mandatory")
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}

}
