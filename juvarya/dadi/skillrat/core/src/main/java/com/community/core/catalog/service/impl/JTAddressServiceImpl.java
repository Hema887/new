package com.community.core.catalog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.profile.core.domain.Address;
import org.springframework.stereotype.Service;

import com.community.core.catalog.dao.JTAddressDao;
import com.community.core.catalog.service.JTAddressService;

@Service("jtAddressService")
public class JTAddressServiceImpl implements JTAddressService {
	
	@Resource(name="jtAddressDao")
	private JTAddressDao jtAddressDao;

	@Override
	public List<Address> getAddresses(int pageNo, int pageSize) {
		return jtAddressDao.getAddresses(pageNo, pageSize);
	}

}
