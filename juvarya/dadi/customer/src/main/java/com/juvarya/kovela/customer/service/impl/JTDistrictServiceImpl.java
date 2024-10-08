package com.juvarya.kovela.customer.service.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.juvarya.kovela.customer.model.JTDistrictModel;
import com.juvarya.kovela.customer.repository.JTDistrictRepository;
import com.juvarya.kovela.customer.service.JTDistrictService;

@Service("jtDistrictService")
public class JTDistrictServiceImpl implements JTDistrictService{
	
	@Resource(name = "jtDistrictRepository")
	private JTDistrictRepository districtRepository;

	@Override
	@Transactional
	public JTDistrictModel saveDistrict(JTDistrictModel model) {
		return districtRepository.save(model);
	}

	@Override
	public JTDistrictModel findById(Long id) {
		try {
			return districtRepository.getById(id);
		} catch (Exception e) {
			return null;
		}
		
	}

	@Override
	public void deleteById(Long id) {
		districtRepository.deleteById(id);
	}

	@Override
	public JTDistrictModel findByIsoCode(String isoCode) {
		return districtRepository.findByIsoCode(isoCode);
	}

}
