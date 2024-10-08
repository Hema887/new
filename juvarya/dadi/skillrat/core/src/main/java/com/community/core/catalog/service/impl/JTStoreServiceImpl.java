package com.community.core.catalog.service.impl;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.community.core.catalog.dao.JTStoreDao;
import com.community.core.catalog.domain.JTStore;
import com.community.core.catalog.domain.Media;
import com.community.core.catalog.service.JTStoreService;

@Service("jtStoreService")
public class JTStoreServiceImpl implements JTStoreService {

	@Resource(name = "jtStoreDao")
	private JTStoreDao jtStoreDao;

	@Override
	public JTStore save(JTStore jtStore) {
		return jtStoreDao.save(jtStore);
	}

	@Override
	public JTStore customerStore(Long customerId) {
		return jtStoreDao.findByCustomer(customerId);
	}

	@Override
	public JTStore findById(Long id) {
		return jtStoreDao.findById(id);
	}

	@Override
	public Media getStoreProfilePicture(Long storeId) {
		try {
			return jtStoreDao.findByStore(storeId);
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public Page<JTStore> getAllStores(Pageable pageable) {
		return jtStoreDao.getAllStores(pageable);
	}
}
