package com.community.core.catalog.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.community.core.catalog.dao.JTCategoryToMediaDao;
import com.community.core.catalog.domain.JTCategoryToMedia;
import com.community.core.config.JTCoreConstants;

@Repository("jtCategoryToMediaDao")
public class JTCategoryToMediaDaoImpl implements JTCategoryToMediaDao {

	@PersistenceContext(unitName = "blPU")
	protected EntityManager em;

	@Override
	@Transactional("blTransactionManager")
	public JTCategoryToMedia save(JTCategoryToMedia categoryToMedia) {
		return em.merge(categoryToMedia);
	}

	@Override
	public JTCategoryToMedia findById(Long id) {
		return em.find(JTCategoryToMedia.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JTCategoryToMedia> findByCategory(Long categoryId) {
		StringBuilder sb = new StringBuilder();
		sb.append(JTCoreConstants.FROM);
		sb.append(" JTCategoryToMediaImpl");
		sb.append(" WHERE category=:categoryId");
		Query query = em.createQuery(sb.toString());
		query.setParameter("categoryId", categoryId);
		query.setHint(QueryHints.HINT_CACHEABLE, true);

		if (CollectionUtils.isNotEmpty(query.getResultList())) {
			return query.getResultList();
		}
		return null;
	}

	@Override
	@Transactional("blTransactionManager")
	public void deleteMedia(JTCategoryToMedia categoryToMedia) {
		em.remove(categoryToMedia);

	}

}
