package com.juvarya.kovela.customer.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.juvarya.kovela.customer.model.JTUserOTPModel;
import com.juvarya.kovela.customer.repository.JTUserOTPRepository;
import com.juvarya.kovela.customer.service.JTUserOTPService;

@Service("jtUserOTPService")
public class JTUserOTPServiceImpl implements JTUserOTPService {

	@Resource(name = "jtUserOTPRepository")
	private JTUserOTPRepository jtUserOTPRepository;

	@Override
	@Transactional
	public JTUserOTPModel save(JTUserOTPModel userOTP) {
		return jtUserOTPRepository.save(userOTP);
	}

	@Override
	public JTUserOTPModel findByEmailAddressAndOtpType(String emailAddress, String otpType) {
		List<JTUserOTPModel> emailOtps= jtUserOTPRepository.findByEmailAddressAndOtpType(emailAddress, otpType, Sort.by(Sort.Direction.DESC, "id"));
		if(CollectionUtils.isEmpty(emailOtps)) {
			return null;
		}
		return emailOtps.get(0);
	}

	@Transactional
	public void deleteOTP(JTUserOTPModel userOtp) {
		jtUserOTPRepository.delete(userOtp);
	}

	@Override
	public List<JTUserOTPModel> findByPrimaryContactAndOtpType(String primaryContact, String otpType) {
		return jtUserOTPRepository.findByPrimaryContactAndOtpType(primaryContact, otpType);
	}

	@Override
	public JTUserOTPModel findByOtpTypeAndPrimaryContact(String otpType, String primaryContact) {
		return jtUserOTPRepository.findByOtpTypeAndPrimaryContact(otpType, primaryContact);
	}

}
