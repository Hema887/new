package com.community.core.catalog.dao;

import java.util.List;

import com.community.core.catalog.domain.JTProductToMedia;

public interface JTProductToMediaDao {

	JTProductToMedia save(JTProductToMedia productToMedia);

	JTProductToMedia findById(Long id);

	List<JTProductToMedia> findByProduct(Long productId);
	
	void deleteMedia(JTProductToMedia jtProductToMedia);
	
}
