package com.juvarya.kovela.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juvarya.kovela.customer.model.JTCityModel;

@Repository("jtCityRepository")
public interface JTCityRepository extends JpaRepository<JTCityModel, Long>{
}
