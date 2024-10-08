package com.community.api.endpoint.data;

import java.util.List;

import com.community.core.catalog.domain.JTCategory;

public class JTCategoryListData {

	private List<JTCategory> categories;

	public List<JTCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<JTCategory> categories) {
		this.categories = categories;
	}

}
