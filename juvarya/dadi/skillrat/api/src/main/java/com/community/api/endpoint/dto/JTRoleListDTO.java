package com.community.api.endpoint.dto;

import java.util.List;

public class JTRoleListDTO extends JTResponseDTO {
	private List<JTRoleDTO> roles;

	public List<JTRoleDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<JTRoleDTO> roles) {
		this.roles = roles;
	}
}
