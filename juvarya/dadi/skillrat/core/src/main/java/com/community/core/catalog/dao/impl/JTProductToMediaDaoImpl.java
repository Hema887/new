package com.community.core.catalog.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.community.core.catalog.dao.JTProductToMediaDao;
import com.community.core.catalog.domain.JTProductToMedia;
import com.community.core.config.JTCoreConstants;

@Repository("jtProductToMediaDao")
public class JTProductToMediaDaoImpl implements JTProductToMediaDao {

	@PersistenceContext(unitName = "blPU")
	protected EntityManager em;

	@Override
	@Transactional("blTransactionManager")
	public JTProductToMedia save(JTProductToMedia productToMedia) {
		return em.merge(productToMedia);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JTProductToMedia> findByProduct(Long productId) {
		StringBuilder sb = new StringBuilder();
		sb.append(JTCoreConstants.FROM);
		sb.append(" JTProductToMediaImpl");
		sb.append(" WHERE product=:productId");
		Query query = em.createQuery(sb.toString());
		query.setParameter("productId", productId);
		query.setHint(QueryHints.HINT_CACHEABLE, true);

		if (CollectionUtils.isNotEmpty(query.getResultList())) {
			return query.getResultList();
		}
		return null;
	}

	@Override
	public JTProductToMedia findById(Long id) {
		return em.find(JTProductToMedia.class, id);
	}

	@Override
	@Transactional
	public void deleteMedia(JTProductToMedia jtProductToMedia) {
		em.remove(jtProductToMedia);		
	}

}
