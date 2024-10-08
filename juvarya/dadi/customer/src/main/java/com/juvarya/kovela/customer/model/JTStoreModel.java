package com.juvarya.kovela.customer.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="JTSTORE")
public class JTStoreModel {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="NAME")
	private String name;
	
	@OneToOne
	@JoinColumn(name="CREATED_USER")
	private User createdBy;
	
	@Enumerated(EnumType.STRING)
    @Column(length = 20)
	private JTStoreType type;
	
	@Column(name="CONTACT_NUMBER")
	private String contactNumber;
	
	@Column(name="CONTACT_NAME")
	private String contactName;

	@Column(name="CREATION_TIME")
	private Date createdDate;
	
	@Column(name="ACTIVE")
	private Boolean active;
	
	@Column(name="PROFILE")
	private Long profile;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public JTStoreType getType() {
		return type;
	}

	public void setType(JTStoreType type) {
		this.type = type;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Long getProfile() {
		return profile;
	}

	public void setProfile(Long profile) {
		this.profile = profile;
	}
}
