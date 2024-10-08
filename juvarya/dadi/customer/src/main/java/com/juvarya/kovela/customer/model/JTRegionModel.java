package com.juvarya.kovela.customer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="JTREGION")
public class JTRegionModel {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="ISOCODE")
	private String isoCode;
	
	@Column(name="NAME")
	private String name;

	@OneToOne
	@JoinColumn(name="COUNTRY")
	private JTCountryModel country;
	
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

	public JTCountryModel getCountry() {
		return country;
	}

	public void setCountry(JTCountryModel country) {
		this.country = country;
	}
}
