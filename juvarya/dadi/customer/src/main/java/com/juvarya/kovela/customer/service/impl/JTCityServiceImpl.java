package com.juvarya.kovela.customer.service.impl;

import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.juvarya.kovela.customer.model.JTCityModel;
import com.juvarya.kovela.customer.repository.JTCityRepository;
import com.juvarya.kovela.customer.service.JTCityService;

@Service("jtCityService")
public class JTCityServiceImpl implements JTCityService {

	@Resource(name = "jtCityRepository")
	private JTCityRepository cityRepository;

	@Override
	@Transactional
	public JTCityModel saveCity(JTCityModel city) {
		return cityRepository.save(city);
	}

	@Override
	public JTCityModel findById(Long id) {
		Optional<JTCityModel> city = cityRepository.findById(id);
		if (city.isPresent()) {
			return city.get();
		}
		return null;
	}

	@Override
	@Transactional
	public void deleteCity(JTCityModel cityModel) {
		cityRepository.delete(cityModel);
	}

}
