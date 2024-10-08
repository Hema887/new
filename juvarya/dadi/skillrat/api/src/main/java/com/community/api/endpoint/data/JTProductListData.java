package com.community.api.endpoint.data;

import java.util.List;

import com.community.core.catalog.domain.JTProduct;

public class JTProductListData {

	private List<JTProduct> products;

	public List<JTProduct> getProducts() {
		return products;
	}

	public void setProducts(List<JTProduct> products) {
		this.products = products;
	}
	
}
