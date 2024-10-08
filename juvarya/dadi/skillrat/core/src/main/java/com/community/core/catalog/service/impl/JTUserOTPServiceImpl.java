package com.community.core.catalog.service.impl;

import javax.annotation.Resource;

import org.broadleafcommerce.common.util.TransactionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.community.core.catalog.dao.JTUserOTPDao;
import com.community.core.catalog.domain.JTCustomer;
import com.community.core.catalog.domain.JTUserOTP;
import com.community.core.catalog.service.JTUserOTPService;

@Service("jtUserOTPService")
public class JTUserOTPServiceImpl implements JTUserOTPService {

	@Resource(name = "jtUserOTPDao")
	private JTUserOTPDao jtUserOTPDao;

	@Override
	@Transactional(TransactionUtils.DEFAULT_TRANSACTION_MANAGER)
	public JTUserOTP save(JTUserOTP jtUserOTP) {
		return jtUserOTPDao.save(jtUserOTP);
	}

	@Override
	public JTUserOTP findById(Long id) {
		return jtUserOTPDao.findById(id);
	}

	@Override
	@Transactional(TransactionUtils.DEFAULT_TRANSACTION_MANAGER)
	public JTUserOTP delete(Long jtUserOTPId) {
		return jtUserOTPDao.delete(jtUserOTPId);
	}

	@Override
	public JTUserOTP findByCustomer(JTCustomer customer) {
		return null;
	}

	@Override
	public JTUserOTP findByEmail(String email) {
		return jtUserOTPDao.findByEmail(email);
	}

	@Override
	public JTUserOTP findByPrimaryContactAndOtpType(String number, String type) {
		return jtUserOTPDao.findByPrimaryContactAndOtpType(number, type);
	}
}
