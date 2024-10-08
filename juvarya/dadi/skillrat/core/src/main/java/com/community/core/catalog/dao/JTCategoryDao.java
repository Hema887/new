package com.community.core.catalog.dao;

import java.util.List;

import com.community.core.catalog.domain.JTCategory;

public interface JTCategoryDao {

	List<JTCategory> categorysList(int page, int pageSize);

	public List<JTCategory> findByPopular(Boolean topLevelCategory, int page, int pageSize);

	List<JTCategory> categorysListByOrder(int pageNo, int pageSize);

}
