package com.community.core.catalog.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.community.core.catalog.dao.JTCustomerLastLoginDao;
import com.community.core.catalog.domain.JTCustomerLastLogin;
import com.community.core.config.JTCoreConstants;

@Repository("jtCustomerLastLoginDao")
public class JTCustomerLastLoginDaoImpl implements JTCustomerLastLoginDao {
	@PersistenceContext(unitName = "blPU")
	protected EntityManager em;

	@Override
	@Transactional
	public JTCustomerLastLogin save(JTCustomerLastLogin customerLastLogin) {
		return em.merge(customerLastLogin);
	}

	@Override
	public JTCustomerLastLogin findByJtCustomer(Long customerId) {
		StringBuilder sb = new StringBuilder();
		sb.append(JTCoreConstants.FROM); 
		sb.append(" JTCustomerLastLoginImpl");
		sb.append(" WHERE jtCustomer.id=:customerId");
		Query query = em.createQuery(sb.toString());
		query.setParameter("customerId", customerId);
		query.setHint(QueryHints.HINT_CACHEABLE, true);

		if (CollectionUtils.isNotEmpty(query.getResultList())) {
			return (JTCustomerLastLogin) query.getResultList().get(0);
		}
		return null;
	}
}
