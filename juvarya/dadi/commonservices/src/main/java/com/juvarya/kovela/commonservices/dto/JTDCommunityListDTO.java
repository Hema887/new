package com.juvarya.kovela.commonservices.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTDCommunityListDTO {

	private List<JTDCommunityDTO> communities;

	public List<JTDCommunityDTO> getCommunities() {
		return communities;
	}

	public void setCommunities(List<JTDCommunityDTO> communities) {
		this.communities = communities;
	}

}
