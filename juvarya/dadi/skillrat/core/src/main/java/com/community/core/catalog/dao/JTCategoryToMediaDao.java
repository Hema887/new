package com.community.core.catalog.dao;

import java.util.List;

import com.community.core.catalog.domain.JTCategoryToMedia;

public interface JTCategoryToMediaDao {

	JTCategoryToMedia save(JTCategoryToMedia categoryToMedia);

	JTCategoryToMedia findById(Long id);

	List<JTCategoryToMedia> findByCategory(Long categoryId);

	void deleteMedia(JTCategoryToMedia categoryToMedia);
}
