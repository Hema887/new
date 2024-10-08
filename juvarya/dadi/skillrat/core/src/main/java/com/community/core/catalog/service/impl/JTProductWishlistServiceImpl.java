package com.community.core.catalog.service.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.community.core.catalog.dao.JTProductWishlistDao;
import com.community.core.catalog.domain.JTProductWishlist;
import com.community.core.catalog.service.JTProductWishlistService;

@Service("jtProductWishlistService")
public class JTProductWishlistServiceImpl  implements JTProductWishlistService {

	
	@Resource(name = "jtProductWishlistDao")
	private JTProductWishlistDao jtProductWishlistDao;
	
	@Override
	@Transactional
	public JTProductWishlist save(JTProductWishlist jtProductWishlist) {
		return jtProductWishlistDao.save(jtProductWishlist);
	}

	@Override
	public JTProductWishlist findById(Long id) {
		return jtProductWishlistDao.findById(id);
	}
	
	@Override
	public JTProductWishlist findByJtCustomerAndJtProduct(Long jtCustomer, Long product) {
		return jtProductWishlistDao.findByJtCustomerAndJtProduct(jtCustomer, product);
	}

	@Override
	public void deleteProduct(JTProductWishlist product) {
		jtProductWishlistDao.deleteProduct(product);	
	}

	@Override
	public long countByJtProductWishList(Long jtProduct) {
		return jtProductWishlistDao.countByJtProductWishList(jtProduct);
	}

	@Override
	public Page<JTProductWishlist> wishlistCustomers(Long customerId, Pageable pageable) {
		try {
			return jtProductWishlistDao.findByJtCustomer(customerId, pageable);
		} catch (Exception e) {
			return null;
		}
	}
}
