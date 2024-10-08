package com.juvarya.kovela.customer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="POSTALCODE")
public class JTPostalCodeModel {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="CODE")
	private Long code;
	
	@OneToOne
	@JoinColumn(name="CITY")
	private JTCityModel city;

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

	public JTCityModel getCity() {
		return city;
	}

	public void setCity(JTCityModel city) {
		this.city = city;
	}
}
