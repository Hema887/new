package com.juvarya.kovela.customer.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTAddressDTO {

	private long id;
	private String contactNumber;
	@NotBlank
	private String line1;
	private String line2;
	private String line3;
	@NotBlank
	private Long postalCode;
	private JTUserDTO dto;
	@NotBlank
	private String locality;
	private Long profileId;
	private Long storeId;
	private Long userId;
	private String addressType;
	private Boolean defaultAddress;
	private JTPostalCodeDTO postalCodeDTO;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getLine3() {
		return line3;
	}

	public void setLine3(String line3) {
		this.line3 = line3;
	}

	public Long getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(Long postalCode) {
		this.postalCode = postalCode;
	}

	public JTUserDTO getDto() {
		return dto;
	}

	public void setDto(JTUserDTO dto) {
		this.dto = dto;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public Boolean getDefaultAddress() {
		return defaultAddress;
	}

	public void setDefaultAddress(Boolean defaultAddress) {
		this.defaultAddress = defaultAddress;
	}

	public JTPostalCodeDTO getPostalCodeDTO() {
		return postalCodeDTO;
	}

	public void setPostalCodeDTO(JTPostalCodeDTO postalCodeDTO) {
		this.postalCodeDTO = postalCodeDTO;
	}
}
