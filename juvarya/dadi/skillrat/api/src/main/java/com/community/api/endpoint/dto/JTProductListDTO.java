package com.community.api.endpoint.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTProductListDTO {

	private List<JTProductDTO> products = new ArrayList<>();
	
	@XmlElement
	private int page;
	
	@XmlElement
	private int pageSize;

	public List<JTProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<JTProductDTO> products) {
		this.products = products;
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
