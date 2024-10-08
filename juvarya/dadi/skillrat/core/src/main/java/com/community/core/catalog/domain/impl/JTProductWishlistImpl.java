package com.community.core.catalog.domain.impl;

import java.util.Date;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.community.core.catalog.domain.JTCustomer;
import com.community.core.catalog.domain.JTProduct;
import com.community.core.catalog.domain.JTProductWishlist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "JT_PRODUCT_WISHLIST")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JTProductWishlistImpl implements JTProductWishlist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, targetEntity = JTCustomerImpl.class, optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY")
	private JTCustomer createdBy;

	@ManyToOne(cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, targetEntity = JTProductImpl.class, optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT")
    private JTProduct product;
    
    @Column(name = "CREATION_TIME")
	private Date creationTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public JTCustomer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(JTCustomer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public JTProduct getProduct() {
		return product;
	}

	public void setProduct(JTProduct product) {
		this.product = product;
	}
}
