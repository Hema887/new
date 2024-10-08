package com.juvarya.kovela.customer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "JT_USER_GROUP",
indexes = {@Index(name = "idx_usergroupid",  columnList="id", unique = true)})
public class JTUserGroupModel {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "CODE")
	private String code;

	@Column(name = "JT_PROFILE")
	private Long profileId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	
}
