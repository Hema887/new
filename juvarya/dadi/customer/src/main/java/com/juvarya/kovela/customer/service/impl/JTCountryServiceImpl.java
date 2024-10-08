package com.juvarya.kovela.customer.service.impl;

import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.juvarya.kovela.customer.model.JTCountryModel;
import com.juvarya.kovela.customer.repository.JTCountryRepository;
import com.juvarya.kovela.customer.service.JTCountryService;

@Service("jtCountryService")
public abstract class JTCountryServiceImpl implements JTCountryService {

	@Resource(name = "jtCountryRepository")
	private JTCountryRepository countryRepository;

	@Override
	@Transactional
	public JTCountryModel saveCountry(JTCountryModel countryModel) {
		return countryRepository.save(countryModel);
	}

	@Override
	public Optional<JTCountryModel> findById(Long id) {
		return countryRepository.findById(id);
	}

	@Override
	public JTCountryModel findByIsoCodeContainsIgnoreCase(String isoCode) {
		return countryRepository.findByIsoCodeContainsIgnoreCase(isoCode);
	}

	@Override
	@Transactional
	public void remove(JTCountryModel countryModel) {
		countryRepository.delete(countryModel);
	}
}
