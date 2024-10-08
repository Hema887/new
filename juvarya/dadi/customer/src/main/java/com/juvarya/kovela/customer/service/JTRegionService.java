package com.juvarya.kovela.customer.service;

import java.util.Optional;

import com.juvarya.kovela.customer.model.JTRegionModel;

public interface JTRegionService {
	JTRegionModel saveRegion(JTRegionModel jtRegionModel);
	Optional<JTRegionModel> findById(Long id);
	JTRegionModel findByIsoCodeContainsIgnoreCase(String isoCode);
	void deleteRegion(JTRegionModel jtRegionModel);
	JTRegionModel getById(Long id);
}
