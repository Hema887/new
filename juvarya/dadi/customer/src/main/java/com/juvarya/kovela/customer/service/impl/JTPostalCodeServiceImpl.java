package com.juvarya.kovela.customer.service.impl;

import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.juvarya.kovela.customer.model.JTPostalCodeModel;
import com.juvarya.kovela.customer.repository.JTPostalCodeRepository;
import com.juvarya.kovela.customer.service.JTPostalCodeService;

@Service("jtPostalCodeService")
public class JTPostalCodeServiceImpl implements JTPostalCodeService {

	@Resource(name = "jtPostalCodeRepository")
	private JTPostalCodeRepository jtPostalCodeRepository;

	@Override
	public JTPostalCodeModel getPostalCode(Long code) {
		try {
			return jtPostalCodeRepository.findByCode(code);
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	@Transactional
	public JTPostalCodeModel save(JTPostalCodeModel postalCode) {
		return jtPostalCodeRepository.save(postalCode);
	}

	@Override
	public JTPostalCodeModel findById(Long id) {
		Optional<JTPostalCodeModel> postalCode = jtPostalCodeRepository.findById(id);
		if (postalCode.isPresent()) {
			return postalCode.get();
		}
		return null;
	}

	@Override
	@Transactional
	public void deletePostalCode(JTPostalCodeModel codeModel) {
		jtPostalCodeRepository.delete(codeModel);
	}
}
