package com.community.core.catalog.dao;

import org.broadleafcommerce.profile.core.domain.Country;

public interface BLCCountryDao {
	
	Country save(Country country);
	
	Country findByCode(String abbreviation);

}
