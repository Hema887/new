package com.community.api.endpoint.dto;

import org.springframework.security.core.AuthenticationException;

public class RoleNotFound extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RoleNotFound(String msg) {
		super(msg);
	}

}
