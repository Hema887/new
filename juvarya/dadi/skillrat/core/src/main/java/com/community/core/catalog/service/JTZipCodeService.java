package com.community.core.catalog.service;

import com.community.core.catalog.domain.JTZipcode;

public interface JTZipCodeService {
	JTZipcode findZipCodeByZipCode(Integer zipCode);
	JTZipcode save(JTZipcode zipcode);
}
