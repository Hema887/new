package com.community.core.azure.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.community.core.azure.service.JTAwsBlobService;
import com.community.core.catalog.domain.Media;
import com.community.core.catalog.domain.impl.MediaModel;
import com.community.core.catalog.service.MediaService;

@Service("jtAwsBlobService")
public class JTAwsBlobServiceImpl implements JTAwsBlobService {

	@Value("${secretKey}")
	private String secretKey;

	@Value("${accessKey}")
	private String accessKey;

	@Value("${bucketName}")
	private String bucketName;

	@Value("${region}")
	private String region;

	@Resource(name = "mediaService")
	private MediaService mediaService;

	public AmazonS3 getClient() {
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.AP_SOUTH_1).build();
	}

	@SuppressWarnings("resource")
	@Override
	public Media uploadFile(MultipartFile file) throws IOException {
		Media media = new MediaModel();
		File modified = new File(file.getOriginalFilename());
		FileOutputStream os = new FileOutputStream(modified);
		os.write(file.getBytes());
		String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
		getClient().putObject(bucketName, fileName, modified);
		modified.delete();
		media.setUrl(getS3Url(fileName));
		return mediaService.create(media);
	}

	@Override
	public List<Media> uploadFiles(List<MultipartFile> files) throws FileNotFoundException, IOException {
		List<Media> uploadedFiles = new ArrayList<>();
		for (MultipartFile multiPartFile : files) {
			Media media = uploadFile(multiPartFile);
			uploadedFiles.add(media);
		}
		return uploadedFiles;
	}

	private String getS3Url(String fileName) {
		String completeUrl = "https://" + bucketName + "." + region + "/" + fileName;
		return completeUrl;
	}

}
