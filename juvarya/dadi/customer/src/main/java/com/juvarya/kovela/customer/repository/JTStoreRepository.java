package com.juvarya.kovela.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juvarya.kovela.customer.model.JTStoreModel;

@Repository("jtStoreRepository")
public interface JTStoreRepository extends JpaRepository<JTStoreModel, Long> {

}
