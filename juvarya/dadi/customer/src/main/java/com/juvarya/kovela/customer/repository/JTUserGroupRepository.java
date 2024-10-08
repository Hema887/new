package com.juvarya.kovela.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juvarya.kovela.customer.model.JTUserGroupModel;

@Repository("jtUserGroupRepository")
public interface JTUserGroupRepository extends JpaRepository<JTUserGroupModel, Long>{

}
