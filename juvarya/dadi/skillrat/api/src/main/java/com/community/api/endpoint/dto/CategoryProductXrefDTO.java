package com.community.api.endpoint.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryProductXrefDTO {

	private Long id;

	private BigDecimal displayOrder;

	private Boolean defaultReference;

	private JTProductDTO jtProduct;

	private JTCategoryDTO jtCategory;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(BigDecimal displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Boolean getDefaultReference() {
		return defaultReference;
	}

	public void setDefaultReference(Boolean defaultReference) {
		this.defaultReference = defaultReference;
	}

	public JTProductDTO getJtProduct() {
		return jtProduct;
	}

	public void setJtProduct(JTProductDTO jtProduct) {
		this.jtProduct = jtProduct;
	}

	public JTCategoryDTO getJtCategory() {
		return jtCategory;
	}

	public void setJtCategory(JTCategoryDTO jtCategory) {
		this.jtCategory = jtCategory;
	}
}
