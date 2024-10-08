package com.community.api.endpoint.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTOTPVerificationDTO extends JTResponseDTO{
	private String userName;
	private String otp;
	private String password;
	private String confirmPassword;
	private boolean updatePassword;
	private String email;
	private boolean updateEmail;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public boolean isUpdatePassword() {
		return updatePassword;
	}
	public void setUpdatePassword(boolean updatePassword) {
		this.updatePassword = updatePassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isUpdateEmail() {
		return updateEmail;
	}
	public void setUpdateEmail(boolean updateEmail) {
		this.updateEmail = updateEmail;
	}
}
