package com.juvarya.kovela.customer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTUserDTO {

	private Long id;
	private String fullName;
	private String email;
	private String primaryContact;
	private String gothra;
	private String type;
	private String picture;
	private JTPostalCodeDTO jtPostalCodeDTO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}

	public String getGothra() {
		return gothra;
	}

	public void setGothra(String gothra) {
		this.gothra = gothra;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public JTPostalCodeDTO getJtPostalCodeDTO() {
		return jtPostalCodeDTO;
	}

	public void setJtPostalCodeDTO(JTPostalCodeDTO jtPostalCodeDTO) {
		this.jtPostalCodeDTO = jtPostalCodeDTO;
	}
}
