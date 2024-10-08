package com.juvarya.kovela.customer.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTDesignationListDTO {
	private List<JTDesignationDTO> designations;

	public List<JTDesignationDTO> getDesignations() {
		return designations;
	}

	public void setDesignations(List<JTDesignationDTO> designations) {
		this.designations = designations;
	}
	
	
}
