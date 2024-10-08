package com.juvarya.kovela.customer.service;

import com.juvarya.kovela.customer.model.JTPostalCodeModel;

public interface JTPostalCodeService {
	JTPostalCodeModel getPostalCode(Long code);
	JTPostalCodeModel save(JTPostalCodeModel postalCode);
	JTPostalCodeModel findById(Long id);
	void deletePostalCode(JTPostalCodeModel codeModel);
}
