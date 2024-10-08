package com.community.core.catalog.dao;

import com.community.core.catalog.domain.JTRole;

public interface RoleDao {
	JTRole findByName(String name);
}
