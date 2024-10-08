package com.community.core.catalog.domain.impl;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.broadleafcommerce.core.store.domain.StoreImpl;

import com.community.core.catalog.domain.JTCustomer;
import com.community.core.catalog.domain.JTStore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "JT_STORE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JTStoreImpl extends StoreImpl implements JTStore {

	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = JTCustomerImpl.class, optional = true)
	@JoinColumn(name = "JT_CUSTOMER")
	private JTCustomer jtCustomer;
	
	@Column(name = "CREATION_TIME")
	private Date creationTime;
	
	@Column(name = "ACTIVE")
	private Boolean active;

	public JTCustomer getJtCustomer() {
		return jtCustomer;
	}

	public void setJtCustomer(JTCustomer jtCustomer) {
		this.jtCustomer = jtCustomer;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
