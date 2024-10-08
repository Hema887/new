package com.community.core.catalog.dao.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;

import com.community.core.catalog.dao.JTCategoryDao;
import com.community.core.catalog.domain.JTCategory;
import com.community.core.config.JTCoreConstants;

@Repository("jtCategoryDao")
public class JTCategoryDaoImpl implements JTCategoryDao {

	@PersistenceContext(unitName = "blPU")
	protected EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<JTCategory> findByPopular(Boolean topLevelCategory, int page, int pageSize) {
		Query query = em.createQuery("FROM JTCategoryImpl WHERE topLevelCategory =:topLevelCategory");
		query.setParameter("topLevelCategory", topLevelCategory);
		query.setFirstResult((page) * pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JTCategory> categorysList(int page, int pageSize) {
		StringBuilder sb = new StringBuilder();
		sb.append("FROM JTCategoryImpl ");

		Query queryForProducts = em.createQuery(sb.toString());
		queryForProducts.setFirstResult((page) * pageSize);
		queryForProducts.setMaxResults(pageSize);
		return queryForProducts.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JTCategory> categorysListByOrder(int pageNo, int pageSize) {
		StringBuilder sb = new StringBuilder();
		sb.append(JTCoreConstants.FROM);
		sb.append(" JTCategoryImpl category");
		sb.append(" WHERE category.active=true ORDER BY category.categoryOrder ASC");
		Query query = em.createQuery(sb.toString());
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		query.setFirstResult((pageNo) * pageSize);
		query.setMaxResults(pageSize);

		if (CollectionUtils.isNotEmpty(query.getResultList())) {
			return query.getResultList();
		}
		return Collections.emptyList();
	}

}
