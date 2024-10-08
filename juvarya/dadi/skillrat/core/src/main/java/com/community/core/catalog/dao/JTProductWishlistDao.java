package com.community.core.catalog.dao;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.community.core.catalog.domain.JTProductWishlist;
import com.community.core.catalog.domain.impl.JTProductWishlistImpl;

public interface JTProductWishlistDao {

	JTProductWishlist save(JTProductWishlist jtProductWishlist);

	JTProductWishlist findById(Long id);

	JTProductWishlist findByJtCustomerAndJtProduct(Long jtCustomer, Long product);
	
	void deleteProduct(JTProductWishlist product);
	
	long countByJtProductWishList(Long jtProduct);

	Page<JTProductWishlist> findByJtCustomer(Long customerId, Pageable pageable);
}
