package com.juvarya.kovela.customer.eventlistener;

import org.springframework.context.ApplicationEvent;

public class JTEvent extends ApplicationEvent {

	public JTEvent(Object source) {
		super(source);
	}

	private static final long serialVersionUID = 1L;
	private String message;
	private String email;
	private String otp;
	private boolean save;
	private boolean update;
	private boolean invite;
	private String otpType;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public boolean isSave() {
		return save;
	}

	public void setSave(boolean save) {
		this.save = save;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public boolean isInvite() {
		return invite;
	}

	public void setInvite(boolean invite) {
		this.invite = invite;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOtpType() {
		return otpType;
	}

	public void setOtpType(String otpType) {
		this.otpType = otpType;
	}
}
