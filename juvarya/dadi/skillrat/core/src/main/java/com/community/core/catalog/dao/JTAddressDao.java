package com.community.core.catalog.dao;

import java.util.List;

import org.broadleafcommerce.profile.core.domain.Address;

public interface JTAddressDao {
	List<Address> getAddresses(int pageNo, int pageSize);

}
