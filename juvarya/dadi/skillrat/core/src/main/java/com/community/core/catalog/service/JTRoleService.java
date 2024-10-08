package com.community.core.catalog.service;

import java.util.Optional;

import com.community.core.catalog.domain.JTRole;
import com.community.core.catalog.domain.impl.ERole;

public interface JTRoleService {
	JTRole findByName(ERole name);
	
	Optional<JTRole> getByName(ERole name);
}
