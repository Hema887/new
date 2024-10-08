package com.juvarya.kovela.commonservices.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTCustomerProfileRoleDTO {
	private Long id;
	private Long customerId;
	private Long profileId;
	private String profileRole;
	private String contactNumber;
	private JTProfileDTO jtProfileDTO;
	private Long jtNotification;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getProfileId() {
		return profileId;
	}
	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}
	public String getProfileRole() {
		return profileRole;
	}
	public void setProfileRole(String profileRole) {
		this.profileRole = profileRole;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public JTProfileDTO getJtProfileDTO() {
		return jtProfileDTO;
	}
	public void setJtProfileDTO(JTProfileDTO jtProfileDTO) {
		this.jtProfileDTO = jtProfileDTO;
	}
	public Long getJtNotification() {
		return jtNotification;
	}
	public void setJtNotification(Long jtNotification) {
		this.jtNotification = jtNotification;
	}
	
}
