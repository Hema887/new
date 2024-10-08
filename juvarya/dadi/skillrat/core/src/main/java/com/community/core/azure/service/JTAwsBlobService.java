package com.community.core.azure.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.community.core.catalog.domain.Media;

public interface JTAwsBlobService {
	AmazonS3 getClient();
	Media uploadFile(MultipartFile file) throws FileNotFoundException, IOException;
	List<Media> uploadFiles(List<MultipartFile> file) throws FileNotFoundException, IOException;
}
