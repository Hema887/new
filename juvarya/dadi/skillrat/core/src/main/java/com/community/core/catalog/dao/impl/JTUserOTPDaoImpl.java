package com.community.core.catalog.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.community.core.catalog.dao.JTUserOTPDao;
import com.community.core.catalog.domain.JTCustomer;
import com.community.core.catalog.domain.JTUserOTP;
import com.community.core.catalog.domain.impl.JTUserOTPImpl;
import com.community.core.config.JTCoreConstants;

@Repository("jtUserOTPDao")
public class JTUserOTPDaoImpl implements JTUserOTPDao {

	@PersistenceContext(unitName = "blPU")
	protected EntityManager em;

	@Override
	@Transactional("blTransactionManager")
	public JTUserOTP save(JTUserOTP jtUser) {
		return em.merge(jtUser);
	}

	@Override
	public JTUserOTP findById(Long id) {
		return em.find(JTUserOTPImpl.class, id);
	}

	@Override
	@Transactional("blTransactionManager")
	public JTUserOTP delete(Long jtUserOTPId) {
		JTUserOTP jtUserOTP = findById(jtUserOTPId);
		if (jtUserOTP != null) {
			em.remove(jtUserOTP);
		}
		return jtUserOTP;
	}

	@Override
	public JTUserOTP findByCustomer(JTCustomer jtCustomer) {
		StringBuilder sb = new StringBuilder();
		sb.append(JTCoreConstants.FROM);
		sb.append(" JTUserOTPImpl");
		sb.append(" WHERE jtCustomer.id =:" + JTCoreConstants.CUSTOMERID);
		Query query = em.createQuery(sb.toString());
		query.setParameter(JTCoreConstants.CUSTOMERID, jtCustomer.getId());
		query.setHint(QueryHints.HINT_CACHEABLE, true);

		if (CollectionUtils.isNotEmpty(query.getResultList())) {
			return (JTUserOTP) query.getResultList().get(0);
		}
		return null;
	}

	@Override
	public JTUserOTP findByEmail(String email) {
		StringBuilder sb = new StringBuilder();
		sb.append(JTCoreConstants.FROM);
		sb.append(" JTUserOTPImpl");
		sb.append(" WHERE emailAddress=:email");
		Query query = em.createQuery(sb.toString());
		query.setParameter("email", email);
		query.setHint(QueryHints.HINT_CACHEABLE, true);

		if (CollectionUtils.isNotEmpty(query.getResultList())) {
			return (JTUserOTP) query.getResultList().get(0);
		}
		return null;
	}

	@Override
	public JTUserOTP findByPrimaryContactAndOtpType(String number, String type) {
		StringBuilder sb = new StringBuilder();
		sb.append(JTCoreConstants.FROM);
		sb.append(" JTUserOTPImpl");
		sb.append(" WHERE primaryContact=:number AND otpType=:type");
		Query query = em.createQuery(sb.toString());
		query.setParameter("number", number);
		query.setParameter("type", type);
		query.setHint(QueryHints.HINT_CACHEABLE, true);

		if (CollectionUtils.isNotEmpty(query.getResultList())) {
			return (JTUserOTP) query.getResultList().get(0);
		}
		return null;
	}
}
