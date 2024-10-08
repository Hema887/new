package com.juvarya.kovela.customer.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "JTCUSTOMER_LAST_LOGIN")
public class JTCustomerLastLoginModel {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "DATE")
	private Date date;

	@OneToOne
	@JoinColumn(name = "JT_CUSTOMER")
	private User jtCustomer;

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

	public User getJtCustomer() {
		return jtCustomer;
	}

	public void setJtCustomer(User jtCustomer) {
		this.jtCustomer = jtCustomer;
	}

}
