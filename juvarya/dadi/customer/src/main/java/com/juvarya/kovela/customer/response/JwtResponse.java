package com.juvarya.kovela.customer.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtResponse {
	private String token;

	private String refreshToken;
	private String type = "Bearer";
	private Long id;
	private String email;
	private List<String> roles;
	private String primaryContact;

	public JwtResponse(String accessToken, Long id, String primaryContact, String email, List<String> roles,
			String refreshToken) {
		this.token = accessToken;
		this.id = id;
		this.primaryContact = primaryContact;
		this.email = email;
		this.roles = roles;
		this.refreshToken = refreshToken;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public List<String> getRoles() {
		return roles;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public String getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}
}
