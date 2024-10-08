package com.community.core.eventlistener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class JTEventPublisher {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	public void publishJTEvent(final String message, String email, String otp, boolean b, boolean update) {
		JTEvent customerEmailEvent = new JTEvent(this);
		customerEmailEvent.setEmail(email);
		customerEmailEvent.setMessage(message);
		customerEmailEvent.setSave(b);
		customerEmailEvent.setOtp(otp);
		customerEmailEvent.setUpdate(update);
		applicationEventPublisher.publishEvent(customerEmailEvent);
	}

	public void publishEmailJTEvent(final String email, boolean emailUpdate) {
		JTEvent customerEmailEvent = new JTEvent(this);
		customerEmailEvent.setEmail(email);
		customerEmailEvent.setEmailUpdate(emailUpdate);
		applicationEventPublisher.publishEvent(customerEmailEvent);
	}

	public void publishJTEvent(final String email, boolean invite) {

		JTEvent customerEmailEvent = new JTEvent(this);
		customerEmailEvent.setEmail(email);
		customerEmailEvent.setInvite(invite);

		applicationEventPublisher.publishEvent(customerEmailEvent);
	}
}