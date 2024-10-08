package com.community.core.eventlistener;

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
	private boolean notification;
	private boolean emailUpdate;
	private boolean invite;

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public boolean isSave() {
		return save;
	}

	public void setSave(boolean save) {
		this.save = save;
	}

	public String getOtp() {
		return otp;
	}

	public String setOtp(String otp) {
		return this.otp = otp;
	}

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

	public boolean isNotification() {
		return notification;
	}

	public void setNotification(boolean notification) {
		this.notification = notification;
	}

	public boolean isEmailUpdate() {
		return emailUpdate;
	}

	public void setEmailUpdate(boolean emailUpdate) {
		this.emailUpdate = emailUpdate;
	}

	public boolean isInvite() {
		return invite;
	}

	public void setInvite(boolean invite) {
		this.invite = invite;
	}

}