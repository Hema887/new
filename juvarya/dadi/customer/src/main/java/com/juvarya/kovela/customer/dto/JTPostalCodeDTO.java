package com.juvarya.kovela.customer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTPostalCodeDTO {
	private Long id;
	private Long code;
	private JTCityDTO city;
	private Long cityId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public JTCityDTO getCity() {
		return city;
	}

	public void setCity(JTCityDTO city) {
		this.city = city;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
}
