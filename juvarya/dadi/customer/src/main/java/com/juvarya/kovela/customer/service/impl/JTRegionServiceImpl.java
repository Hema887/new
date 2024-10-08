package com.juvarya.kovela.customer.service.impl;

import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.juvarya.kovela.customer.model.JTRegionModel;
import com.juvarya.kovela.customer.repository.JTRegionRepository;
import com.juvarya.kovela.customer.service.JTRegionService;

@Service("jtRegionService")
public class JTRegionServiceImpl implements JTRegionService {

	@Resource(name = "jtRegionRepository")
	private JTRegionRepository jtRegionRepository;

	@Override
	@Transactional
	public JTRegionModel saveRegion(JTRegionModel jtRegionModel) {
		return jtRegionRepository.save(jtRegionModel);
	}

	@Override
	public Optional<JTRegionModel> findById(Long id) {
		return jtRegionRepository.findById(id);
	}

	@Override
	public JTRegionModel findByIsoCodeContainsIgnoreCase(String isoCode) {
		return jtRegionRepository.findByIsoCodeContainsIgnoreCase(isoCode);
	}

	@Override
	@Transactional
	public void deleteRegion(JTRegionModel jtRegionModel) {
		jtRegionRepository.delete(jtRegionModel);
	}

	@Override
	public JTRegionModel getById(Long id) {
		try {
			return jtRegionRepository.getById(id);
		} catch (Exception e) {
			return null;
		}

	}

}
