package com.juvarya.kovela.customer.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.juvarya.kovela.customer.model.JTStoreModel;

public interface JTStoreService {

	JTStoreModel save(JTStoreModel model);

	Optional<JTStoreModel> findById(Long id);

	Page<JTStoreModel> getAll(Pageable pageable);

	void deleteStore(Long id);
}
