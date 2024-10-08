package com.community.core.catalog.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;

import com.community.core.catalog.dao.JTCustomerRoleDao;
import com.community.core.catalog.domain.JTCustomerRole;
import com.community.core.config.JTCoreConstants;

@Repository("jtCustomerRoleDao")
public class JTCustomerRoleDaoImpl implements JTCustomerRoleDao {
	@PersistenceContext(unitName = "blPU")
	protected EntityManager em;

	@Override
	public JTCustomerRole save(JTCustomerRole customerRole) {
		return em.merge(customerRole);
	}

	@Override
	public JTCustomerRole getCustomerRoles(Long customerId) {
		StringBuilder sb = new StringBuilder();
		sb.append(JTCoreConstants.FROM);
		sb.append(" JTCustomerRoleImpl");
		sb.append(" WHERE jtCustomer.id=:customerId");
		Query query = em.createQuery(sb.toString());
		query.setParameter("customerId", customerId);
		query.setHint(QueryHints.HINT_CACHEABLE, true);

		if (CollectionUtils.isNotEmpty(query.getResultList())) {
			return (JTCustomerRole) query.getResultList().get(0);
		}
		return null;
	}

}
