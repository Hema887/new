package com.community.core.catalog.service.impl;

import javax.annotation.Resource;

import org.broadleafcommerce.core.offer.dao.OfferCodeDao;
import org.broadleafcommerce.core.offer.domain.OfferCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.community.core.catalog.service.JTOfferCodeService;

@Service("jtOfferCodeService")
public class JTOfferCodeServiceImpl implements JTOfferCodeService {
	@Resource(name = "blOfferCodeDao")
	private OfferCodeDao offerCodeDao;

	@Override
	@Transactional
	public OfferCode saveOfferCode(OfferCode offerCode) {
		return offerCodeDao.save(offerCode);
	}

}
