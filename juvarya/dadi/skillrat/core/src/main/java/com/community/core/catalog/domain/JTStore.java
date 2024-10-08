package com.community.core.catalog.domain;

import java.util.Date;

import org.broadleafcommerce.core.store.domain.Store;

public interface JTStore extends Store{
	public JTCustomer getJtCustomer();
	public void setJtCustomer(JTCustomer jtCustomer);
	public Date getCreationTime();
	public void setCreationTime(Date creationTime);
	public Boolean getActive();
	public void setActive(Boolean active);
} 
