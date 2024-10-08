package com.community.core.catalog.domain.impl;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.community.core.catalog.domain.JTCustomer;
import com.community.core.catalog.domain.JTCustomerDate;

@Entity
@Table(name = "JTCUSTOMER_DATE")
public class JTCustomerDateImpl implements JTCustomerDate {
	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "DATE")
	private Date date;

	@ManyToOne(cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, targetEntity = JTCustomerImpl.class, optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "jt_CUSTOMER")
	private JTCustomer jtCustomer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public JTCustomer getJtCustomer() {
		return jtCustomer;
	}

	public void setJtCustomer(JTCustomer jtCustomer) {
		this.jtCustomer = jtCustomer;
	}

}
