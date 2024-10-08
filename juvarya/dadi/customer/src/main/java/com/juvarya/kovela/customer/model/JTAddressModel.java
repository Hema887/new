package com.juvarya.kovela.customer.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.juvarya.kovela.model.JTAddressType;

@Entity
@Table(name = "ADDRESS",
indexes = {@Index(name = "idx_addressid",  columnList="id", unique = true)})
public class JTAddressModel {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "CONTACT_NUMBER")
	private String contactNumber;

	@Column(name = "LOCALITY")
	private String locality;

	@Column(name = "LINE1")
	private String line1;

	@Column(name = "LINE2")
	private String line2;

	@Column(name = "LINE3")
	private String line3;

	@OneToOne
	@JoinColumn(name="POSTALCODE")
	private JTPostalCodeModel postalCode;

	@OneToOne
	@JoinColumn(name = "JT_USER")
	private User user;
	
	@Column(name = "CREATION_TIME")
	private Date creationTime;

	@Enumerated(EnumType.STRING)
    @Column(length = 20, name="ADDRESS_TYPE")
	private JTAddressType addressType;
	
	@Column(name = "TARGET_ID")
	private Long targetId;
	
	@Column(name = "DEFAULT_ADDRESS")
	private Boolean defaultAddress;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public JTPostalCodeModel getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(JTPostalCodeModel postalCode) {
		this.postalCode = postalCode;
	}

	public JTAddressType getAddressType() {
		return addressType;
	}

	public void setAddressType(JTAddressType addressType) {
		this.addressType = addressType;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public Boolean getDefaultAddress() {
		return defaultAddress;
	}

	public void setDefaultAddress(Boolean defaultAddress) {
		this.defaultAddress = defaultAddress;
	}
}
