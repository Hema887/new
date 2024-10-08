package com.juvarya.kovela.customer.service.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.juvarya.kovela.customer.model.JTAddressModel;
import com.juvarya.kovela.customer.repository.JTAddressRepository;
import com.juvarya.kovela.customer.service.JTAddressService;
import com.juvarya.kovela.model.JTAddressType;

@Service("jtAddressService")
public class JTAddressServiceImpl implements JTAddressService {

	@Resource(name = "jtAddressRepository")
	private JTAddressRepository addressRepository;

	@Override
	public JTAddressModel saveAddress(JTAddressModel address) {
		return addressRepository.save(address);
	}

	@Override
	public List<JTAddressModel> getSavedAddresses(Iterable<Long> id) {
		return (List<JTAddressModel>) addressRepository.findAllById(id);
	}

	@Override
	public JTAddressModel findById(Long id) {
		Optional<JTAddressModel> address = addressRepository.findById(id);
		if (address.isPresent()) {
			return address.get();
		}
		return null;
	}

	@Override
	public Page<JTAddressModel> findByUser(Long userId, Pageable pageable) {
		return addressRepository.findByUser(userId, pageable);
	}

	@Override
	public JTAddressModel findByTargetId(Long targetId, Boolean defaultAddress) {
		return addressRepository.findByTargetIdAndDefaultAddress(targetId, defaultAddress);
	}

	@Override
	public List<JTAddressModel> findByTargetId(Long targetId) {
		return addressRepository.findByTargetId(targetId);
	}

	@Override
	public Page<JTAddressModel> findNearByProfiles(String isoCode, JTAddressType addressType, Pageable page) {
		return addressRepository.findByPostalCodeAndAddressType(isoCode, addressType, page);
	}

	@Override
	@Transactional
	public void deleteAddress(JTAddressModel addressModel) {
		addressRepository.delete(addressModel);
	}

}
