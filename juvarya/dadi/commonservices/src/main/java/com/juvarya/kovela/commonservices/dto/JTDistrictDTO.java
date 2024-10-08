package com.juvarya.kovela.commonservices.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTDistrictDTO {
	private Long id;
	private String name;
	private String isoCode;
	private Long regionId;
	private JTRegionDTO regionDTO;

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

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public JTRegionDTO getRegionDTO() {
		return regionDTO;
	}

	public void setRegionDTO(JTRegionDTO regionDTO) {
		this.regionDTO = regionDTO;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
}
