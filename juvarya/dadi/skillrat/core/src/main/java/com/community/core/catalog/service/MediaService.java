package com.community.core.catalog.service;

import com.community.core.catalog.domain.Media;

public interface MediaService {
	Media create(Media media);
	void removeMedia(Media media);
}
