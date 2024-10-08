package com.community.api.endpoint.data;

import java.util.List;

import org.broadleafcommerce.profile.core.domain.Address;

public class JTAddressListData {
	private List<Address> addresses;

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

}
