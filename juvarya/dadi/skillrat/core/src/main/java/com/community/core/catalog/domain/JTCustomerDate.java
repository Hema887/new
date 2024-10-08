package com.community.core.catalog.domain;

import java.util.Date;

public interface JTCustomerDate {

	public Long getId();
	public void setId(Long id);
	public Date getDate();
	public void setDate(Date date);
	public JTCustomer getJtCustomer();
	public void setJtCustomer(JTCustomer jtCustomer);

}
