package com.juvarya.kovela.customer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "JTCITY")
public class JTCityModel {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "ISOCODE")
	private String isoCode;

	@Column(name = "NAME")
	private String name;

	@OneToOne
	@JoinColumn(name = "DISTRICT")
	private JTDistrictModel district;

	@Column(name = "HAS_AIR_TRANSPORT")
	private Boolean hasAirTransport;

	@Column(name = "HAS_RAIL_TRANSPORT")
	private Boolean hasRailTransport;

	@Column(name = "HAS_BUS_TRANSPORT")
	private Boolean hasBusTransport;

	@OneToOne
	@JoinColumn(name = "NEAREST_RAIL_TRANSPORT_CITY")
	private JTCityModel nearestRailTransportCity;

	@OneToOne
	@JoinColumn(name = "NEAREST_AIR_TRANSPORT_CITY")
	private JTCityModel nearestAirTransportCity;

	@OneToOne
	@JoinColumn(name = "NEAREST_BUS_TRANSPORT_CITY")
	private JTCityModel nearestBusTransportCity;

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

	public JTDistrictModel getDistrict() {
		return district;
	}

	public void setDistrict(JTDistrictModel district) {
		this.district = district;
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

	public JTCityModel getNearestRailTransportCity() {
		return nearestRailTransportCity;
	}

	public void setNearestRailTransportCity(JTCityModel nearestRailTransportCity) {
		this.nearestRailTransportCity = nearestRailTransportCity;
	}

	public JTCityModel getNearestAirTransportCity() {
		return nearestAirTransportCity;
	}

	public void setNearestAirTransportCity(JTCityModel nearestAirTransportCity) {
		this.nearestAirTransportCity = nearestAirTransportCity;
	}

	public JTCityModel getNearestBusTransportCity() {
		return nearestBusTransportCity;
	}

	public void setNearestBusTransportCity(JTCityModel nearestBusTransportCity) {
		this.nearestBusTransportCity = nearestBusTransportCity;
	}
}
