package com.community.core.catalog.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.broadleafcommerce.core.order.dao.OrderDaoImpl;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.type.OrderStatus;
import org.hibernate.jpa.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.community.core.catalog.dao.JTOrderDao;
import com.community.core.config.JTCoreConstants;

@Repository("jtOrderDao")
public class JTOrderDaoImpl extends OrderDaoImpl implements JTOrderDao {

	@SuppressWarnings("unchecked")
	@Override
	public Page<Order> findAllOrders(OrderStatus status, Pageable pageable) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(JTCoreConstants.FROM);
		stringBuilder.append(" OrderImpl ");
		stringBuilder.append(" WHERE status =:status");
		Query queryForCourse = em.createQuery(stringBuilder.toString());

		queryForCourse.setFirstResult((int) pageable.getOffset());
		queryForCourse.setFirstResult(pageable.getPageNumber());
		queryForCourse.setMaxResults(pageable.getPageSize());
		queryForCourse.setHint(QueryHints.HINT_CACHEABLE, true);
		queryForCourse.setParameter("status", status.getType());

		if (CollectionUtils.isNotEmpty(queryForCourse.getResultList())) {
			List<Order> resultList = queryForCourse.getResultList();
			Query countQuery = em.createQuery("SELECT COUNT(id) FROM OrderImpl WHERE status =:status");
			countQuery.setParameter("status", status.getType());
			long totalCount = (long) countQuery.getSingleResult();
			return new PageImpl<>(resultList, pageable, totalCount);
		}
		return null;
	}

}
