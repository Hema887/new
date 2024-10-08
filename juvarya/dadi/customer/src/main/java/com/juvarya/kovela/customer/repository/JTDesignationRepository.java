package com.juvarya.kovela.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juvarya.kovela.customer.model.JTDesignationModel;

@Repository("jtDesignationRepository")
public interface JTDesignationRepository extends JpaRepository<JTDesignationModel, Long>{

}