package com.juvarya.kovela.customer.service;

import com.juvarya.kovela.customer.model.JTDistrictModel;

public interface JTDistrictService {
	
	JTDistrictModel saveDistrict(JTDistrictModel model);
	JTDistrictModel findById(Long id);
	void deleteById(Long id);
	JTDistrictModel findByIsoCode(String isoCode);
}
