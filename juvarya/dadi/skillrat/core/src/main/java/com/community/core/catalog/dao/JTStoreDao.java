package com.community.core.catalog.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.community.core.catalog.domain.JTStore;
import com.community.core.catalog.domain.Media;

public interface JTStoreDao {

	JTStore save(JTStore jtStore);

	JTStore findByCustomer(Long customerId);

	JTStore findById(Long id);

	Media findByStore(Long storeId);
	
	Page<JTStore> getAllStores(Pageable pageable);
}
