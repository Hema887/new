package com.community.core.catalog.domain.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.broadleafcommerce.core.store.domain.ZipCodeImpl;

import com.community.core.catalog.domain.JTZipcode;

@Entity
@Table(name = "JT_Zipcode")
public class JTZipcodeImpl extends ZipCodeImpl implements JTZipcode {
	private static final long serialVersionUID = 1L;

	@Column(name = "JT_ZIPCODE")
	private Integer jtzipcode;

	@Column(name = "JT_ZIP_STATE")
	private String jtzipState;

	public Integer getJtzipcode() {
		return jtzipcode;
	}

	public void setJtzipcode(Integer jtzipcode) {
		this.jtzipcode = jtzipcode;
	}

	public String getJtzipState() {
		return jtzipState;
	}

	public void setJtzipState(String jtzipState) {
		this.jtzipState = jtzipState;
	}

}
