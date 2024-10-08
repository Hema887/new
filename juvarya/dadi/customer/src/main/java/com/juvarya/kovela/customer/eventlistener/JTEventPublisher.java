package com.juvarya.kovela.customer.eventlistener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class JTEventPublisher {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	public void publishJTEvent(final String message, String email, String otp, boolean b, boolean update,
			boolean invite, String otpType) {

		System.out.println("Publishing JTEvent. ");
		JTEvent customerEmailEvent = new JTEvent(this);
		customerEmailEvent.setEmail(email);
		customerEmailEvent.setMessage(message);
		customerEmailEvent.setSave(b);
		customerEmailEvent.setOtp(otp);
		customerEmailEvent.setUpdate(update);
		customerEmailEvent.setInvite(invite);
		customerEmailEvent.setOtpType(otpType);
		applicationEventPublisher.publishEvent(customerEmailEvent);
	}
	
}
