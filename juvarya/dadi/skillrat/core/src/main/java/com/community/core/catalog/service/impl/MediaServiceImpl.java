package com.community.core.catalog.service.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.community.core.catalog.dao.MediaDao;
import com.community.core.catalog.domain.Media;
import com.community.core.catalog.service.MediaService;

@Service("mediaService")
public class MediaServiceImpl implements MediaService {

	@Resource(name = "mediaDao")
	private MediaDao mediaDao;
	
	@Override
	@Transactional
	public Media create(Media media) {
		return mediaDao.saveMedia(media);
	}

	@Override
	@Transactional
	public void removeMedia(Media media) {
		mediaDao.deleteMedia(media);
	}

}
