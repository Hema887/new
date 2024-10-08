package com.community.core.catalog.domain;

import java.util.Date;

public interface JTProductWishlist {

	public Long getId();
	public void setId(Long id);
	public JTProduct getProduct();
	public void setProduct(JTProduct product);
	public JTCustomer getCreatedBy() ;
	public void setCreatedBy(JTCustomer createdBy);
	public Date getCreationTime();
	public void setCreationTime(Date creationTime);
}
