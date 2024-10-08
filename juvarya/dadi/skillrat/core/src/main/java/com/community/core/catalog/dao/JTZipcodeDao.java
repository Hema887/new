package com.community.core.catalog.dao;

import com.community.core.catalog.domain.JTZipcode;

public interface JTZipcodeDao {
	JTZipcode findZipCodeByZipCode(Integer zipCode);
	JTZipcode save(JTZipcode zipcode);

}
