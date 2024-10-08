package com.juvarya.kovela.commonservices.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class JTProfileDTO {
	private Long id;
	private String name;
	private String code;
	private String description;
	private String templeClass;
	private String establishedOn;
	private Boolean approved;
	private Date creationTime;
	private Date lastUpdatedOn;
	private Boolean seasonal;
	private String logo;
	private Boolean popular;
	private Boolean servicesEnabled;
	private Boolean donationsEnabled;
	private Boolean ecommerceEnabled;
	private Boolean reelsEnabled;
	private Boolean eventsEnabled;
	private String line1;
	private Long community;
	private JTDCommunityDTO communityDTO;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEstablishedOn() {
		return establishedOn;
	}

	public void setEstablishedOn(String establishedOn) {
		this.establishedOn = establishedOn;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public Boolean getSeasonal() {
		return seasonal;
	}

	public void setSeasonal(Boolean seasonal) {
		this.seasonal = seasonal;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Boolean getPopular() {
		return popular;
	}

	public void setPopular(Boolean popular) {
		this.popular = popular;
	}

	public Boolean getServicesEnabled() {
		return servicesEnabled;
	}

	public void setServicesEnabled(Boolean servicesEnabled) {
		this.servicesEnabled = servicesEnabled;
	}

	public Boolean getDonationsEnabled() {
		return donationsEnabled;
	}

	public void setDonationsEnabled(Boolean donationsEnabled) {
		this.donationsEnabled = donationsEnabled;
	}

	public Boolean getEcommerceEnabled() {
		return ecommerceEnabled;
	}

	public void setEcommerceEnabled(Boolean ecommerceEnabled) {
		this.ecommerceEnabled = ecommerceEnabled;
	}

	public Boolean getReelsEnabled() {
		return reelsEnabled;
	}

	public void setReelsEnabled(Boolean reelsEnabled) {
		this.reelsEnabled = reelsEnabled;
	}

	public Boolean getEventsEnabled() {
		return eventsEnabled;
	}

	public void setEventsEnabled(Boolean eventsEnabled) {
		this.eventsEnabled = eventsEnabled;
	}

	public String getTempleClass() {
		return templeClass;
	}

	public void setTempleClass(String templeClass) {
		this.templeClass = templeClass;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public Long getCommunity() {
		return community;
	}

	public void setCommunity(Long community) {
		this.community = community;
	}

	public JTDCommunityDTO getCommunityDTO() {
		return communityDTO;
	}

	public void setCommunityDTO(JTDCommunityDTO communityDTO) {
		this.communityDTO = communityDTO;
	}
}
