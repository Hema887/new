package com.community.core.catalog.service;

import java.util.List;

import com.community.core.catalog.domain.JTCategory;

public interface JTCategoryService {

	List<JTCategory> CategorysList(int page, int pageSize);

	public List<JTCategory> findByPopular(Boolean topLevelCategory, int page, int pageSize);

	List<JTCategory> categorysListByOrder(int pageNo, int pageSize);

}
