package com.community.core.catalog.domain.impl;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.core.catalog.domain.ProductImpl;

import com.community.core.catalog.domain.JTCustomer;
import com.community.core.catalog.domain.JTProduct;
import com.community.core.catalog.domain.JTStore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "JT_PRODUCT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JTProductImpl extends ProductImpl implements JTProduct {
	private static final long serialVersionUID = 1L;

	@Column(name = "PRODUCT_TYPE")
	@AdminPresentation(friendlyName = "ProductImpl_Product_Type", group = GroupName.General)
	private String productType;

	@Column(name = "PRODUCT_SHORT_DESCRIPTION")
	@AdminPresentation(friendlyName = "ProductImpl_Product_Short_Description", group = GroupName.General)
	private String shortDescription;

	@Column(name = "PRODUCT_LONG_DESCRIPTION")
	@AdminPresentation(friendlyName = "ProductImpl_Product_Long_Description", group = GroupName.General)
	private String longDescription;

	@Column(name = "POPULAR")
	private Boolean popular;

	@Column(name = "ACTIVE")
	private boolean active;

	@ManyToOne(cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, targetEntity = JTCustomerImpl.class, optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY")
	private JTCustomer createdBy;

	@ManyToOne(cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, targetEntity = JTStoreImpl.class, optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "JT_STORE")
	private JTStore jtStore;

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public Boolean getPopular() {
		return popular;
	}

	public void setPopular(Boolean popular) {
		this.popular = popular;
	}

	public JTCustomer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(JTCustomer createdBy) {
		this.createdBy = createdBy;
	}

	public JTStore getJtStore() {
		return jtStore;
	}

	public void setJtStore(JTStore jtStore) {
		this.jtStore = jtStore;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
