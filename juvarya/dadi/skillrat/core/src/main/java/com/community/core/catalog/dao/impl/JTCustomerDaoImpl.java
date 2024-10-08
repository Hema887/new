package com.community.core.catalog.dao.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.broadleafcommerce.common.persistence.EntityConfiguration;
import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;

import com.community.core.catalog.dao.JTCustomerDao;
import com.community.core.catalog.domain.JTCustomer;
import com.community.core.catalog.domain.impl.JTCustomerImpl;
import com.community.core.config.JTCoreConstants;

@Repository("jtCustomerDao")
public class JTCustomerDaoImpl implements JTCustomerDao {
	@PersistenceContext(unitName = "blPU")
	protected EntityManager em;

	@Resource(name = "blEntityConfiguration")
	protected EntityConfiguration entityConfiguration;

	@Override
	@Transactional
	public JTCustomer save(JTCustomer customer) {
		return em.merge(customer);
	}

	@Override
	public JTCustomer findCustomer(Long id) {
		return em.find(JTCustomerImpl.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JTCustomer> getCustomers(int pageNo, int pageSize) {
		StringBuilder sb = new StringBuilder();
		sb.append(JTCoreConstants.FROM);
		sb.append(" JTCustomerImpl ");
		Query query = em.createQuery(sb.toString());
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		query.setFirstResult(pageNo);
		query.setMaxResults(pageSize);

		if (CollectionUtils.isNotEmpty(query.getResultList())) {
			return query.getResultList();
		}
		return null;
	}

	@Override
	public Long customerCount() {
		Query query = em.createQuery("SELECT COUNT (*) FROM JTCustomerImpl");
		return (long) query.getResultList().get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JTCustomer> getTrainers(Boolean popular, int pageNo, int pageSize) {
		StringBuilder sb = new StringBuilder();
		sb.append(JTCoreConstants.FROM);
		sb.append(" JTCustomerImpl");
		sb.append(" WHERE instructor=true AND popular =:popular");
		Query query = em.createQuery(sb.toString());
		query.setParameter("popular", popular);
		query.setFirstResult((pageNo) * pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();

	}

	@Override
	public JTCustomer findByTrainer(Long trainerId) {
		StringBuilder sb = new StringBuilder();
		sb.append(JTCoreConstants.FROM);
		sb.append(" JTCustomerImpl");
		sb.append(" WHERE instructor=true AND id = :trainerId");
		Query query = em.createQuery(sb.toString());
		query.setParameter("trainerId", trainerId);
		query.setHint(QueryHints.HINT_CACHEABLE, true);

		if (CollectionUtils.isNotEmpty(query.getResultList())) {
			return (JTCustomer) query.getResultList().get(0);
		}
		return null;
	}

	@Override
	public JTCustomer findById(Long id) {
		return em.find(JTCustomerImpl.class, id);
	}

	@Override
	public JTCustomer findByPrimaryContact(String contact) {
		StringBuilder sb = new StringBuilder();
		sb.append(JTCoreConstants.FROM);
		sb.append(" JTCustomerImpl");
		sb.append(" WHERE username=:contact");
		Query query = em.createQuery(sb.toString());
		query.setParameter("contact", contact);
		query.setHint(QueryHints.HINT_CACHEABLE, true);

		if (CollectionUtils.isNotEmpty(query.getResultList())) {
			return (JTCustomer) query.getResultList().get(0);
		}
		return null;
	}

	@Override
	public JTCustomer findByEmail(String email) {
		StringBuilder sb = new StringBuilder();
		sb.append(JTCoreConstants.FROM);
		sb.append(" JTCustomerImpl");
		sb.append(" WHERE email=:email");
		Query query = em.createQuery(sb.toString());
		query.setParameter("email", email);
		query.setHint(QueryHints.HINT_CACHEABLE, true);

		if (CollectionUtils.isNotEmpty(query.getResultList())) {
			return (JTCustomer) query.getResultList().get(0);
		}
		return null;
	}

}
