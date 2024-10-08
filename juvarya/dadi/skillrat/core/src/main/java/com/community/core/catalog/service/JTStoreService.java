package com.community.core.catalog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.community.core.catalog.domain.JTStore;
import com.community.core.catalog.domain.Media;

public interface JTStoreService {

	JTStore save(JTStore jtStore);

	JTStore customerStore(Long customerId);

	JTStore findById(Long id);

	Media getStoreProfilePicture(Long storeId);
	
	Page<JTStore> getAllStores(Pageable pageable);
}
