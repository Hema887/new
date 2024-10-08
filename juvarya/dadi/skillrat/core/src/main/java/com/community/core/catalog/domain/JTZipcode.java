package com.community.core.catalog.domain;

import org.broadleafcommerce.core.store.domain.ZipCode;

public interface JTZipcode extends ZipCode {
	public Integer getJtzipcode();

	public void setJtzipcode(Integer jtzipcode);

	public String getJtzipState();

	public void setJtzipState(String jtzipState);

}
