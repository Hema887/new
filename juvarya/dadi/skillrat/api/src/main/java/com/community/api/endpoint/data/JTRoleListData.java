package com.community.api.endpoint.data;

import java.util.List;

import com.community.core.catalog.domain.JTRole;

public class JTRoleListData {
	private List<JTRole> roles;

	public List<JTRole> getRoles() {
		return roles;
	}

	public void setRoles(List<JTRole> roles) {
		this.roles = roles;
	}
}
