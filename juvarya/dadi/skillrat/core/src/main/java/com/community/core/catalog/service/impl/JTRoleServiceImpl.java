package com.community.core.catalog.service.impl;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.community.core.catalog.dao.JTRoleDao;
import com.community.core.catalog.domain.JTRole;
import com.community.core.catalog.domain.impl.ERole;
import com.community.core.catalog.service.JTRoleService;

@Service("jtRoleService")
public class JTRoleServiceImpl implements JTRoleService {

	@Resource(name = "jtRoleDao")
	private JTRoleDao jtRoleDao;

	@Override
	public JTRole findByName(ERole name) {
		return jtRoleDao.findByName(name);
	}

	@Override
	public Optional<JTRole> getByName(ERole name) {
		return jtRoleDao.getByName(name);
	}

}
