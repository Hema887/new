package com.community.core.catalog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.community.core.catalog.dao.JTCategoryDao;
import com.community.core.catalog.domain.JTCategory;
import com.community.core.catalog.service.JTCategoryService;

@Service("jtCategoryService")
public class JTCategoryServiceImpl implements JTCategoryService {

	@Resource(name = "jtCategoryDao")
	private JTCategoryDao jtCategoryDao;

	@Override
	public List<JTCategory> findByPopular(Boolean topLevelCategory, int page, int pageSize) {
		return jtCategoryDao.findByPopular(topLevelCategory, page, pageSize);
	}

	@Override
	public List<JTCategory> CategorysList(int page, int pageSize) {
		return jtCategoryDao.categorysList(page, pageSize);
	}

	@Override
	public List<JTCategory> categorysListByOrder(int pageNo, int pageSize) {
		return jtCategoryDao.categorysListByOrder(pageNo, pageSize);
	}

}
