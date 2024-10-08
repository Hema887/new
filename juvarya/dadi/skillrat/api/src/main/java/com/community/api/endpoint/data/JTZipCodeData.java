package com.community.api.endpoint.data;

import org.broadleafcommerce.profile.core.domain.Country;
import org.broadleafcommerce.profile.core.domain.CountrySubdivision;

public class JTZipCodeData {
	private String id;
	private Integer zipCode;
	private CountrySubdivision region;
	private CountrySubdivision area;
	private CountrySubdivision city;
	private CountrySubdivision district;
	private CountrySubdivision state;
	private Country country;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getZipCode() {
		return zipCode;
	}
	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}
	public CountrySubdivision getRegion() {
		return region;
	}
	public void setRegion(CountrySubdivision region) {
		this.region = region;
	}
	public CountrySubdivision getArea() {
		return area;
	}
	public void setArea(CountrySubdivision area) {
		this.area = area;
	}
	public CountrySubdivision getCity() {
		return city;
	}
	public void setCity(CountrySubdivision city) {
		this.city = city;
	}
	public CountrySubdivision getDistrict() {
		return district;
	}
	public void setDistrict(CountrySubdivision district) {
		this.district = district;
	}
	public CountrySubdivision getState() {
		return state;
	}
	public void setState(CountrySubdivision state) {
		this.state = state;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
}
