package com.community.core.catalog.domain;

public interface JTCustomerRole {
	public Long getId();
	public void setId(Long id);
	public String getRoleName();
	public void setRoleName(String roleName);
	public JTCustomer getJtCustomer();
	public void setJtCustomer(JTCustomer jtCustomer);
}
