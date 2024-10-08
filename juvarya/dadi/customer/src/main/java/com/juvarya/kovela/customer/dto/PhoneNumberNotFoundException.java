package com.juvarya.kovela.customer.dto;

import org.springframework.security.core.AuthenticationException;

public class PhoneNumberNotFoundException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PhoneNumberNotFoundException(String msg) {
		super(msg);
	}

}
