package com.juvarya.kovela.customer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="JTDISTRICT")
public class JTDistrictModel {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="NAME")
	private String name;

	@Column(name="ISOCODE")
	private String isoCode;
	
	@OneToOne
	@JoinColumn(name="REGION")
	private JTRegionModel region;
	
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

	public JTRegionModel getRegion() {
		return region;
	}

	public void setRegion(JTRegionModel region) {
		this.region = region;
	}
}
