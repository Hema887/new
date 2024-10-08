package com.juvarya.kovela.commonservices.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTFollowerDTO {
	private Long id;
	private String type;
	private Long jtProfile;
	private boolean following;
	private Long customerId;
	private JTProfileDTO jtProfileDTO;
	private JTUserDTO user;
	private Boolean status;
	private Date creationTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getJtProfile() {
		return jtProfile;
	}

	public void setJtProfile(Long jtProfile) {
		this.jtProfile = jtProfile;
	}

	public boolean isFollowing() {
		return following;
	}

	public void setFollowing(boolean following) {
		this.following = following;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public JTProfileDTO getJtProfileDTO() {
		return jtProfileDTO;
	}

	public void setJtProfileDTO(JTProfileDTO jtProfileDTO) {
		this.jtProfileDTO = jtProfileDTO;
	}

	public JTUserDTO getUser() {
		return user;
	}

	public void setUser(JTUserDTO user) {
		this.user = user;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
}
