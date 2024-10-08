package com.community.core.catalog.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.community.core.catalog.dao.MediaDao;
import com.community.core.catalog.domain.Media;

@Repository("mediaDao")
public class MediaDaoImpl implements MediaDao {
 
	@PersistenceContext(unitName = "blPU")
	protected EntityManager em;
	
	@Override
	@Transactional("blTransactionManager")
	public Media saveMedia(Media Media) {
		return em.merge(Media);
	}

	@Override
	public void deleteMedia(Media media) {
		em.remove(media);
	}


}
