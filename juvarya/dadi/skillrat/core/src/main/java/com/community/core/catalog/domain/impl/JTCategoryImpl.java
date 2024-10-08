package com.community.core.catalog.domain.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.broadleafcommerce.core.catalog.domain.CategoryImpl;

import com.community.core.catalog.domain.JTCategory;

@Entity
@Table(name = "JT_CATEGORY")
public class JTCategoryImpl extends CategoryImpl implements JTCategory {

	private static final long serialVersionUID = 1L;

	@Column(name = "TOP_LEVEL_CATEGORY")
	private Boolean topLevelCategory;

	@Column(name = "CATEGORY_ORDER")
	private int categoryOrder;

	@Column(name = "ACTIVE")
	private boolean active;

	public Boolean getTopLevelCategory() {
		return topLevelCategory;
	}

	public void setTopLevelCategory(Boolean topLevelCategory) {
		this.topLevelCategory = topLevelCategory;
	}

	public int getCategoryOrder() {
		return categoryOrder;
	}

	public void setCategoryOrder(int categoryOrder) {
		this.categoryOrder = categoryOrder;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
