package com.community.api.endpoint.dto;

public class JTResponseDTO {
	private String statusCode;
	private String message;
	private boolean verificationPending;
	
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isVerificationPending() {
		return verificationPending;
	}
	public void setVerificationPending(boolean verificationPending) {
		this.verificationPending = verificationPending;
	}
}
