package com.community.core.catalog.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.broadleafcommerce.profile.core.domain.Address;
import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;

import com.community.core.catalog.dao.JTAddressDao;
import com.community.core.config.JTCoreConstants;

@Repository("jtAddressDao")
public class JTAddressDaoImpl implements JTAddressDao {

	@PersistenceContext(unitName = "blPU")
	protected EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Address> getAddresses(int pageNo, int pageSize) {
		StringBuilder sb = new StringBuilder();
		sb.append(JTCoreConstants.FROM);
		sb.append(" AddressImpl ");
		Query query = em.createQuery(sb.toString());
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		query.setFirstResult(pageNo);
		query.setMaxResults(pageSize);

		if (CollectionUtils.isNotEmpty(query.getResultList())) {
			return query.getResultList();
		}
		return null;
	}

}
