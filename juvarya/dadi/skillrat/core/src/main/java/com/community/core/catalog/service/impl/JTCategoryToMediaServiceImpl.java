package com.community.core.catalog.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.community.core.azure.service.JTAwsBlobService;
import com.community.core.catalog.dao.JTCategoryToMediaDao;
import com.community.core.catalog.domain.JTCategoryToMedia;
import com.community.core.catalog.domain.Media;
import com.community.core.catalog.domain.impl.JTCategoryToMediaImpl;
import com.community.core.catalog.service.JTCategoryToMediaService;

@Service("jtCategoryToMediaService")
public class JTCategoryToMediaServiceImpl implements JTCategoryToMediaService {

	@Resource(name = "jtCategoryToMediaDao")
	private JTCategoryToMediaDao categoryToMediaDao;

	@Resource(name = "jtAwsBlobService")
	private JTAwsBlobService jtAwsBlobService;

	@Override
	public JTCategoryToMedia save(JTCategoryToMedia categoryToMedia) {
		return categoryToMediaDao.save(categoryToMedia);
	}

	@Override
	public JTCategoryToMedia findByid(Long id) {
		return categoryToMediaDao.findById(id);
	}

	@Override
	public List<JTCategoryToMedia> findByCategory(Long categoryId) {
		return categoryToMediaDao.findByCategory(categoryId);
	}

	@Override
	public void deleteMedia(JTCategoryToMedia categoryToMedia) {
		categoryToMediaDao.deleteMedia(categoryToMedia);
	}

	@Override
	public JTCategoryToMedia upload(Long categoryId, List<MultipartFile> files)
			throws FileNotFoundException, IOException {
		JTCategoryToMedia categoryToMedia = new JTCategoryToMediaImpl();
		if (!CollectionUtils.isEmpty(files)) {
			List<Media> uploadedFiles = jtAwsBlobService.uploadFiles(files);
			for (Media media : uploadedFiles) {
				JTCategoryToMedia categoryMedia = new JTCategoryToMediaImpl();
				categoryMedia.setCategory(categoryId);
				categoryMedia.setMedia(media);
				categoryToMedia = categoryToMediaDao.save(categoryMedia);
			}
		}
		return categoryToMedia;
	}

}
