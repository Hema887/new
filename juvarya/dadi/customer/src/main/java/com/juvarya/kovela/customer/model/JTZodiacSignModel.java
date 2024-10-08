package com.juvarya.kovela.customer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "JTZODIAC_SIGN")
public class JTZodiacSignModel {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "ZODIAC")
	private String zodiacSign;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getZodiacSign() {
		return zodiacSign;
	}

	public void setZodiacSign(String zodiacSign) {
		this.zodiacSign = zodiacSign;
	}
	
	
}
