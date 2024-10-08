package com.juvarya.kovela.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juvarya.kovela.customer.model.JTZodiacSignModel;

@Repository("jtZodiacSignRepository")
public interface JTZodiacSignRepository extends JpaRepository<JTZodiacSignModel, Long>{

}
