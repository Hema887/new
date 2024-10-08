package com.juvarya.kovela.customer.service;

import com.juvarya.kovela.customer.model.JTCityModel;

public interface JTCityService {
	JTCityModel saveCity(JTCityModel city);

	JTCityModel findById(Long id);

	void deleteCity(JTCityModel cityModel);
}
