package com.community.core.catalog.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.community.core.catalog.dao.JTZipcodeDao;
import com.community.core.catalog.domain.JTZipcode;
import com.community.core.config.JTCoreConstants;

@Repository("jtZipcodeDao")
public class JTZipcodeDaoImpl implements JTZipcodeDao {
	
	@PersistenceContext(unitName = "blPU")
    protected EntityManager em;

	@Override
	public JTZipcode save(JTZipcode zipcode) {
		JTZipcode zip=em.merge(zipcode);
		return zip;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JTZipcode findZipCodeByZipCode(Integer zipCode) {
		StringBuilder sb = new StringBuilder();
		sb.append(JTCoreConstants.FROM);
		sb.append(" JTZipcodeImpl zipcode");
		sb.append(" WHERE zipcode.jtzipcode =:zipCode");
		Query query = em.createQuery(sb.toString());
        query.setParameter("zipCode", zipCode);
        List<JTZipcode> result = query.getResultList();
        return (result.size() > 0) ? result.get(0) : null;
	}


}
