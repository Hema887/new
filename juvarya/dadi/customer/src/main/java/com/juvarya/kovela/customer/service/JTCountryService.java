package com.juvarya.kovela.customer.service;

import java.util.Optional;

import com.juvarya.kovela.customer.model.JTCountryModel;
import com.juvarya.kovela.customer.model.StudentModel;

public interface JTCountryService {
	JTCountryModel saveCountry(JTCountryModel countryModel);
	Optional<JTCountryModel> findById(Long id);
	JTCountryModel findByIsoCodeContainsIgnoreCase(String isoCode);
	void remove(JTCountryModel countryModel);
	StudentModel saveStudent(StudentModel studentModel);
}
