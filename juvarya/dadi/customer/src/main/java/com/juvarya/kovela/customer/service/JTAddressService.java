package com.juvarya.kovela.customer.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.juvarya.kovela.customer.model.JTAddressModel;
import com.juvarya.kovela.model.JTAddressType;

public interface JTAddressService {
	JTAddressModel saveAddress(JTAddressModel address);

	List<JTAddressModel> getSavedAddresses(Iterable<Long> id);

	Page<JTAddressModel> findByUser(Long userId, Pageable pageable);

	JTAddressModel findById(Long id);

	JTAddressModel findByTargetId(Long targetId, Boolean defaultAddress);

	List<JTAddressModel> findByTargetId(Long targetId);

	Page<JTAddressModel> findNearByProfiles(String isoCode, JTAddressType addressType, Pageable page);

	void deleteAddress(JTAddressModel addressModel);
}
