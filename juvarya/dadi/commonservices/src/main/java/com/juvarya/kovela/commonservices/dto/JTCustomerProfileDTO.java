package com.juvarya.kovela.commonservices.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTCustomerProfileDTO {
	private List<String> roles;
	private List<JTCustomerProfileRoleDTO> customerRoles;

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<JTCustomerProfileRoleDTO> getCustomerRoles() {
		return customerRoles;
	}

	public void setCustomerRoles(List<JTCustomerProfileRoleDTO> customerRoles) {
		this.customerRoles = customerRoles;
	}

}
