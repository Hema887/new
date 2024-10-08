package com.juvarya.kovela.commonservices.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTProfileHistoryDTO {

	private Long id;
	
	private String history;
	
	private Long profileId;
	
	private JTProfileDTO jtProfileDTO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	public JTProfileDTO getJtProfileDTO() {
		return jtProfileDTO;
	}

	public void setJtProfileDTO(JTProfileDTO jtProfileDTO) {
		this.jtProfileDTO = jtProfileDTO;
	}

}
