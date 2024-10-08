package com.juvarya.kovela.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juvarya.kovela.customer.model.JTRegionModel;

@Repository("jtRegionRepository")
public interface JTRegionRepository extends JpaRepository<JTRegionModel, Long>{
	JTRegionModel findByIsoCodeContainsIgnoreCase(String isoCode);
}
