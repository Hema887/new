package com.juvarya.kovela.commonservices.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(value = Include.NON_NULL)
@Data
public class LoggedInUser {
	private Long id;

	private String email;

	private String password;

	private String fullName;

	private Set<String> roles = new HashSet<>();

	private Long profileToCustomerId;

	private String customerProfileUrl;

	private String primaryContact;

	private Date dob;

	private String gender;

	private String gothra;

	private Boolean whatsAppEnabled;

	private Long postalCode;

	private JTNotificationListDTO jtNotifications;

	private JTMediaDTO media;

	private boolean newUser;

	private String profilePicture;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public Long getProfileToCustomerId() {
		return profileToCustomerId;
	}

	public void setProfileToCustomerId(Long profileToCustomerId) {
		this.profileToCustomerId = profileToCustomerId;
	}

	public String getCustomerProfileUrl() {
		return customerProfileUrl;
	}

	public void setCustomerProfileUrl(String customerProfileUrl) {
		this.customerProfileUrl = customerProfileUrl;
	}

	public String getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public JTNotificationListDTO getJtNotifications() {
		return jtNotifications;
	}

	public void setJtNotifications(JTNotificationListDTO jtNotifications) {
		this.jtNotifications = jtNotifications;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGothra() {
		return gothra;
	}

	public void setGothra(String gothra) {
		this.gothra = gothra;
	}

	public Boolean getWhatsAppEnabled() {
		return whatsAppEnabled;
	}

	public void setWhatsAppEnabled(Boolean whatsAppEnabled) {
		this.whatsAppEnabled = whatsAppEnabled;
	}

	public JTMediaDTO getMedia() {
		return media;
	}

	public void setMedia(JTMediaDTO media) {
		this.media = media;
	}

	public Long getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(Long postalCode) {
		this.postalCode = postalCode;
	}

	public boolean isNewUser() {
		return newUser;
	}

	public void setNewUser(boolean newUser) {
		this.newUser = newUser;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

}
