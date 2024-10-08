package com.community.core.catalog.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.community.core.catalog.domain.JTCategoryToMedia;

public interface JTCategoryToMediaService {

	JTCategoryToMedia save(JTCategoryToMedia categoryToMedia);

	JTCategoryToMedia upload(Long categoryId, List<MultipartFile> files) throws FileNotFoundException, IOException;

	JTCategoryToMedia findByid(Long id);

	List<JTCategoryToMedia> findByCategory(Long categoryId);

	void deleteMedia(JTCategoryToMedia categoryToMedia);
}
