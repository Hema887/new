package com.community.api.endpoint.dto;

import javax.xml.bind.annotation.XmlElement;

public class BLCCountryDto {

	@XmlElement
	private  String abbreviation;
	
	@XmlElement
	private String name;

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
