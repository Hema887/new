package com.community.core.catalog.dao.impl;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;

import com.community.core.catalog.dao.JTRoleDao;
import com.community.core.catalog.domain.JTRole;
import com.community.core.catalog.domain.impl.ERole;
import com.community.core.config.JTCoreConstants;

@Repository("jtRoleDao")
public class JTRoleDaoImpl implements JTRoleDao {

	@PersistenceContext(unitName = "blPU")
	protected EntityManager em;

	@Override
	public JTRole findByName(ERole name) {
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

	@Override
	public Optional<JTRole> getByName(ERole name) {
		StringBuilder sb = new StringBuilder();
		sb.append(JTCoreConstants.FROM);
		sb.append(" JTRoleImpl");
		sb.append(" WHERE name=:name");
		Query query = em.createQuery(sb.toString());
		query.setParameter("name", name);
		query.setHint(QueryHints.HINT_CACHEABLE, true);

		if (CollectionUtils.isNotEmpty(query.getResultList())) {
			return Optional.ofNullable((JTRole) query.getResultList().get(0));
		}
		return Optional.empty();
	}

}
