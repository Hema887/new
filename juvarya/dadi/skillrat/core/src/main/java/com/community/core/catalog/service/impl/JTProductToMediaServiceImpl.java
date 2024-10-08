package com.community.core.catalog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.community.core.catalog.dao.JTProductToMediaDao;
import com.community.core.catalog.domain.JTProductToMedia;
import com.community.core.catalog.service.JTProductToMediaService;

@Service("jtProductToMediaService")
public class JTProductToMediaServiceImpl implements JTProductToMediaService {

	@Resource(name = "jtProductToMediaDao")
	private JTProductToMediaDao jtProductToMediaDao;

	@Override
	public JTProductToMedia save(JTProductToMedia productToMedia) {
		return jtProductToMediaDao.save(productToMedia);
	}

	@Override
	public List<JTProductToMedia> getProductImages(Long productId) {
		try {
			return jtProductToMediaDao.findByProduct(productId);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void removeMedia(JTProductToMedia jtProductToMedia) {
		jtProductToMediaDao.deleteMedia(jtProductToMedia);
	}

}
