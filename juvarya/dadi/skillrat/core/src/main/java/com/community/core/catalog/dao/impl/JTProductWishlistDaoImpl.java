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

import com.community.core.catalog.dao.JTProductWishlistDao;
import com.community.core.catalog.domain.JTProduct;
import com.community.core.catalog.domain.JTProductWishlist;
import com.community.core.catalog.domain.JTStore;
import com.community.core.catalog.domain.impl.JTProductWishlistImpl;
import com.community.core.config.JTCoreConstants;

@Repository("jtProductWishlistDao")
public class JTProductWishlistDaoImpl implements JTProductWishlistDao {

	@PersistenceContext(unitName = "blPU")
	protected EntityManager em;

	@Override
	@Transactional("blTransactionManager")
	public JTProductWishlist save(JTProductWishlist jtProductWishlist) {
		return em.merge(jtProductWishlist);
	}

	@Override
	public JTProductWishlistImpl findById(Long id) {
		return em.find(JTProductWishlistImpl.class, id);
	}

	@Override
	public JTProductWishlist findByJtCustomerAndJtProduct(Long jtCustomer, Long jtProduct) {
		StringBuilder sb = new StringBuilder();
		sb.append(JTCoreConstants.FROM);
		sb.append(" JTProductWishlistImpl");
		sb.append(" WHERE createdBy.id = :jtCustomer AND product.id = :jtProduct");
		Query query = em.createQuery(sb.toString());
		query.setParameter("jtCustomer", jtCustomer);
		query.setParameter("jtProduct", jtProduct);
		query.setHint(QueryHints.HINT_CACHEABLE, true);

		if (CollectionUtils.isNotEmpty(query.getResultList())) {
			return  (JTProductWishlist) query.getResultList().get(0);
		}
		return null;

	}

	@Override
	@Transactional
	public void deleteProduct(JTProductWishlist product) {
		em.remove(product);
	}

	@Override
	public long countByJtProductWishList(Long jtProduct) {	
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(JTCoreConstants.FROM);
		stringBuilder.append(" JTProductWishlistImpl");
		stringBuilder.append(" WHERE product.id=:jtProduct");
		Query queryForCourse = em.createQuery(stringBuilder.toString());

		queryForCourse.setParameter("jtProduct", jtProduct);
		queryForCourse.setHint(QueryHints.HINT_CACHEABLE, true);

		if (CollectionUtils.isNotEmpty(queryForCourse.getResultList())) {
			List<JTProductWishlist> resultList = queryForCourse.getResultList();
			Query countQuery = em.createQuery("SELECT COUNT(id) FROM JTProductWishlistImpl WHERE product.id=:jtProduct");
			countQuery.setParameter("jtProduct", jtProduct);
			long totalCount = (long) countQuery.getSingleResult();
			return totalCount;
		}
		return 0;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page<JTProductWishlist> findByJtCustomer(Long customerId, Pageable pageable) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(JTCoreConstants.FROM);
		stringBuilder.append(" JTProductWishlistImpl");
		Query queryForCourse = em.createQuery(stringBuilder.toString());
		if (CollectionUtils.isNotEmpty(queryForCourse.getResultList())) {
			List<JTProductWishlist> resultList = queryForCourse.getResultList();
			Query countQuery = em.createQuery("SELECT COUNT(id) FROM JTProductWishlistImpl WHERE createdBy.id = :customerId");
			countQuery.setParameter("customerId", customerId);
			queryForCourse.setFirstResult((int) pageable.getOffset());
			queryForCourse.setMaxResults(pageable.getPageSize());
			queryForCourse.setHint(QueryHints.HINT_CACHEABLE, true);
			long totalCount = (long) countQuery.getSingleResult();
			return new PageImpl(resultList, pageable, totalCount);  
		}
		return null;
	}
}
