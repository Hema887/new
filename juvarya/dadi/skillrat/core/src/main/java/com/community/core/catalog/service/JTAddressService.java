package com.community.core.catalog.service;

import java.util.List;

import org.broadleafcommerce.profile.core.domain.Address;

public interface JTAddressService {
	List<Address> getAddresses(int pageNo, int pageSize);

}
