package com.community.core.catalog.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.community.core.catalog.domain.JTProductWishlist;

public interface JTProductWishlistService {

	JTProductWishlist save(JTProductWishlist jtProductWishlist);

	JTProductWishlist findById(Long id);
	
	JTProductWishlist findByJtCustomerAndJtProduct(Long jtCustomer, Long product);

	void deleteProduct(JTProductWishlist product);
	
	long countByJtProductWishList(Long jtProduct);
	
	Page<JTProductWishlist> wishlistCustomers(Long customerId, Pageable pageable);
}
