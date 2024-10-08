package com.juvarya.kovela.customer.service.impl;

import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.juvarya.kovela.customer.model.JTStoreModel;
import com.juvarya.kovela.customer.repository.JTStoreRepository;
import com.juvarya.kovela.customer.service.JTStoreService;

@Service("jtStoreService")
public class JTStoreServiceImpl implements JTStoreService {

	@Resource(name = "jtStoreRepository")
	private JTStoreRepository repository;

	@Override
	@Transactional
	public JTStoreModel save(JTStoreModel model) {
		return repository.save(model);
	}

	@Override
	public Optional<JTStoreModel> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<JTStoreModel> getAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public void deleteStore(Long id) {
		repository.deleteById(id);
	}

}
