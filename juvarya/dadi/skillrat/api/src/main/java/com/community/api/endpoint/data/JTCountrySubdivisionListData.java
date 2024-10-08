package com.community.api.endpoint.data;

import java.util.List;

import org.broadleafcommerce.profile.core.domain.CountrySubdivision;

public class JTCountrySubdivisionListData {
	private List<CountrySubdivision> countrySubDivisions;

	public List<CountrySubdivision> getCountrySubDivisions() {
		return countrySubDivisions;
	}

	public void setCountrySubDivisions(List<CountrySubdivision> countrySubDivisions) {
		this.countrySubDivisions = countrySubDivisions;
	}
}
