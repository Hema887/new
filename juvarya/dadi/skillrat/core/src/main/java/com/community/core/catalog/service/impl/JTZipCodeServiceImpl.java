package com.community.core.catalog.service.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.community.core.catalog.dao.JTZipcodeDao;
import com.community.core.catalog.domain.JTZipcode;
import com.community.core.catalog.service.JTZipCodeService;

@Service("jtZipCodeService")
public class JTZipCodeServiceImpl implements JTZipCodeService{
	
	@Resource(name="jtZipcodeDao")
	private JTZipcodeDao jtZipcodeDao;

	@Override
	public JTZipcode findZipCodeByZipCode(Integer zipCode) {
		return jtZipcodeDao.findZipCodeByZipCode(zipCode);
	}

	@Override
	@Transactional
	public JTZipcode save(JTZipcode zipcode) {
		return jtZipcodeDao.save(zipcode);
	}

}
