package com.community.core.catalog.domain;

import java.util.Date;
import java.util.Set;

import org.broadleafcommerce.profile.core.domain.Customer;

import com.community.core.catalog.domain.impl.EType;

public interface JTCustomer extends Customer{
	Boolean getPopular();
	void setPopular(Boolean popular);
	public EType getType();
	public void setType(EType type);
	public String getCustomerType();
	public void setCustomerType(String customerType);
	public Set<JTRole> getRoles();
	public void setRoles(Set<JTRole> roles);
	public String getEmail();
	public void setEmail(String email);
	public Date getCreationTime();
	public void setCreationTime(Date creationTime);
	public String getFullName();
	public void setFullName(String fullName);
}
