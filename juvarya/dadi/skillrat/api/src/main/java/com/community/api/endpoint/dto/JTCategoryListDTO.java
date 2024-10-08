package com.community.api.endpoint.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTCategoryListDTO {

	private List<JTCategoryDTO> categories;

	private int page;
	private int pageSize;

	public List<JTCategoryDTO> getCategories() {
		return categories;
	}

	public void setCategories(List<JTCategoryDTO> categories) {
		this.categories = categories;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
