package com.community.core.catalog.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;

import com.community.core.catalog.dao.RoleDao;
import com.community.core.catalog.domain.JTRole;
import com.community.core.config.JTCoreConstants;

@Repository("roleDao")
public class RoleDaoImpl implements RoleDao {
	@PersistenceContext(unitName = "blPU")
	protected EntityManager em;

	@Override
	public JTRole findByName(String name) {
		StringBuilder sb = new StringBuilder();
		sb.append(JTCoreConstants.FROM);
		sb.append(" JTRoleImpl");
		sb.append(" WHERE name=:name");
		Query query = em.createQuery(sb.toString());
		query.setParameter("name", name);
		query.setHint(QueryHints.HINT_CACHEABLE, true);

		if (CollectionUtils.isNotEmpty(query.getResultList())) {
			return (JTRole) query.getResultList().get(0);
		}
		return null;
	}
}
