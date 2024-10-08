package com.community.core.catalog.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.jpa.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.community.core.catalog.dao.JTStoreDao;
import com.community.core.catalog.domain.JTStore;
import com.community.core.catalog.domain.Media;
import com.community.core.catalog.domain.impl.JTStoreImpl;
import com.community.core.config.JTCoreConstants;

@Repository("jtStoreDao")
public class JTStoreDaoImpl implements JTStoreDao {
	@PersistenceContext(unitName = "blPU")
	protected EntityManager em;

	@Override
	@Transactional("blTransactionManager")
	public JTStore save(JTStore jtStore) {
		return em.merge(jtStore);
	}

	@Override
	public JTStore findByCustomer(Long customerId) {
		StringBuilder sb = new StringBuilder();
		sb.append(JTCoreConstants.FROM);
		sb.append(" JTStoreImpl");
		sb.append(" WHERE jtCustomer.id=:customerId");
		Query query = em.createQuery(sb.toString());
		query.setParameter("customerId", customerId);
		query.setHint(QueryHints.HINT_CACHEABLE, true);

		if (CollectionUtils.isNotEmpty(query.getResultList())) {
			return (JTStore) query.getResultList().get(0);
		}
		return null;
	}

	@Override
	public JTStore findById(Long id) {
		return em.find(JTStoreImpl.class, id);
	}

	@Override
	public Media findByStore(Long storeId) {
		StringBuilder sb = new StringBuilder();
		sb.append(JTCoreConstants.FROM);
		sb.append(" MediaModel");
		sb.append(" WHERE jtStore=:storeId");
		Query query = em.createQuery(sb.toString());
		query.setParameter("storeId", storeId);
		query.setHint(QueryHints.HINT_CACHEABLE, true);

		if (CollectionUtils.isNotEmpty(query.getResultList())) {
			return (Media) query.getResultList().get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<JTStore> getAllStores(Pageable pageable) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(JTCoreConstants.FROM);
		stringBuilder.append(" JTStoreImpl");
		Query queryForCourse = em.createQuery(stringBuilder.toString());

		queryForCourse.setFirstResult((int) pageable.getOffset());
		queryForCourse.setMaxResults(pageable.getPageSize());
		queryForCourse.setHint(QueryHints.HINT_CACHEABLE, true);

		if (CollectionUtils.isNotEmpty(queryForCourse.getResultList())) {
			List<JTStore> resultList = queryForCourse.getResultList();
			Query countQuery = em.createQuery("SELECT COUNT(id) FROM JTStoreImpl");
			long totalCount = (long) countQuery.getSingleResult();
			return new PageImpl<>(resultList, pageable, totalCount);
		}
		return null;
	}
}
