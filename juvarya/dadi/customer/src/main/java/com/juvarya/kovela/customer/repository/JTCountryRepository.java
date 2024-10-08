package com.juvarya.kovela.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.juvarya.kovela.customer.model.JTCountryModel;

@Service("jtCountryRepository")
public interface JTCountryRepository extends JpaRepository<JTCountryModel, Long>{
	JTCountryModel findByIsoCodeContainsIgnoreCase(String isoCode);
}
