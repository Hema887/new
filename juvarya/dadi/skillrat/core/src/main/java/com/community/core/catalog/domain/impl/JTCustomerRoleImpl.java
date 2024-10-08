package com.community.core.catalog.domain.impl;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.community.core.catalog.domain.JTCustomer;
import com.community.core.catalog.domain.JTCustomerRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "JT_CUSTOMER_ROLE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JTCustomerRoleImpl implements JTCustomerRole {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "ROLE_NAME")
	private String roleName;

	@ManyToOne(cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, targetEntity = JTCustomerImpl.class, optional = true)
	@JoinColumn(name = "JT_CUSTOMER")
	private JTCustomer jtCustomer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public JTCustomer getJtCustomer() {
		return jtCustomer;
	}

	public void setJtCustomer(JTCustomer jtCustomer) {
		this.jtCustomer = jtCustomer;
	}

}
