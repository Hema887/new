package com.juvarya.kovela.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juvarya.kovela.customer.model.JTPostalCodeModel;

@Repository("jtPostalCodeRepository")
public interface JTPostalCodeRepository extends JpaRepository<JTPostalCodeModel, Long> {
	JTPostalCodeModel findByCode(Long code);
}
