package com.community.core.catalog.domain.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.broadleafcommerce.profile.core.domain.CustomerImpl;

import com.community.core.catalog.domain.JTCustomer;
import com.community.core.catalog.domain.JTRole;

@Entity
@Table(name = "JT_CUSTOMER")
public class JTCustomerImpl extends CustomerImpl implements JTCustomer {
	private static final long serialVersionUID = 1034242271853286957L;

	@Column(name = "POPULAR")
	private Boolean popular;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private EType type;

	@Column(name = "CUSTOMER_TYPE")
	private String customerType;

	@ManyToMany(cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, targetEntity = JTRoleImpl.class, fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<JTRole> roles = new HashSet<>();

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "CREATION_TIME")
	private Date creationTime;

	@Column(name = "FULL_NAME")
	private String fullName;

	public Boolean getPopular() {
		return popular;
	}

	public void setPopular(Boolean popular) {
		this.popular = popular;
	}

	public EType getType() {
		return type;
	}

	public void setType(EType type) {
		this.type = type;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public Set<JTRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<JTRole> roles) {
		this.roles = roles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
