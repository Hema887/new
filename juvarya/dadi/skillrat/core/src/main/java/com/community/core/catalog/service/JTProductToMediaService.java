package com.community.core.catalog.service;

import java.util.List;

import com.community.core.catalog.domain.JTProductToMedia;

public interface JTProductToMediaService {
	JTProductToMedia save(JTProductToMedia productToMedia);

	List<JTProductToMedia> getProductImages(Long productId);
	
	void removeMedia(JTProductToMedia jtProductToMedia);

}
