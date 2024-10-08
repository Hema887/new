package com.community.core.catalog.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.broadleafcommerce.profile.core.dao.CustomerPhoneDaoImpl;
import org.broadleafcommerce.profile.core.domain.CustomerPhone;
import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;

import com.community.core.catalog.dao.JTCustomerPhoneDao;
import com.community.core.config.JTCoreConstants;

@Repository("jtCustomerPhoneDao")
public class JTCustomerPhoneDaoImpl extends CustomerPhoneDaoImpl implements JTCustomerPhoneDao {
	@PersistenceContext(unitName = "blPU")
	protected EntityManager em;

	@Override
	public CustomerPhone findByPhone(String contact) {
		StringBuilder sb = new StringBuilder();
		sb.append(JTCoreConstants.FROM);
		sb.append(" CustomerPhoneImpl");
		sb.append(" WHERE phone.phoneNumber=:contact");
		Query query = em.createQuery(sb.toString());
		query.setParameter("contact", contact);
		query.setHint(QueryHints.HINT_CACHEABLE, true);

		if (CollectionUtils.isNotEmpty(query.getResultList())) {
			return (CustomerPhone) query.getResultList().get(0);
		}
		return null;
	}

}
