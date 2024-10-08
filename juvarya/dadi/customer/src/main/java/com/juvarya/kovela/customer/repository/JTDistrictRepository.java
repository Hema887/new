package com.juvarya.kovela.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juvarya.kovela.customer.model.JTDistrictModel;

@Repository("jtDistrictRepository")
public interface JTDistrictRepository extends JpaRepository<JTDistrictModel, Long>{
	JTDistrictModel findByIsoCode(String isoCode);
}
