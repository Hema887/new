package com.juvarya.kovela.commonservices.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTCityDTO {

	private Long id;
	private String isoCode;
	private String name;
	private Long postalCode;
	private Long region;
	private Boolean hasAirTransport;
	private Boolean hasRailTransport;
	private Boolean hasBusTransport;
	private JTCityDTO nearestAirTransport;
	private JTCityDTO nearestRailTransport;
	private JTCityDTO nearestBusTransport;
	private Long districtId;
	private JTDistrictDTO district;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(Long postalCode) {
		this.postalCode = postalCode;
	}

	public Long getRegion() {
		return region;
	}

	public void setRegion(Long region) {
		this.region = region;
	}

	public Boolean getHasAirTransport() {
		return hasAirTransport;
	}

	public void setHasAirTransport(Boolean hasAirTransport) {
		this.hasAirTransport = hasAirTransport;
	}

	public Boolean getHasRailTransport() {
		return hasRailTransport;
	}

	public void setHasRailTransport(Boolean hasRailTransport) {
		this.hasRailTransport = hasRailTransport;
	}

	public Boolean getHasBusTransport() {
		return hasBusTransport;
	}

	public void setHasBusTransport(Boolean hasBusTransport) {
		this.hasBusTransport = hasBusTransport;
	}

	public JTCityDTO getNearestAirTransport() {
		return nearestAirTransport;
	}

	public void setNearestAirTransport(JTCityDTO nearestAirTransport) {
		this.nearestAirTransport = nearestAirTransport;
	}

	public JTCityDTO getNearestRailTransport() {
		return nearestRailTransport;
	}

	public void setNearestRailTransport(JTCityDTO nearestRailTransport) {
		this.nearestRailTransport = nearestRailTransport;
	}

	public JTCityDTO getNearestBusTransport() {
		return nearestBusTransport;
	}

	public void setNearestBusTransport(JTCityDTO nearestBusTransport) {
		this.nearestBusTransport = nearestBusTransport;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public JTDistrictDTO getDistrict() {
		return district;
	}

	public void setDistrict(JTDistrictDTO district) {
		this.district = district;
	}
}
