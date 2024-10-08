package com.juvarya.kovela.commonservices.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTProfileHistoryToMediaDTO {

	private Long id;

	private Long jtHistory;

	private Long media;

	private List<MultipartFile> files;

	private List<String> url;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getJtHistory() {
		return jtHistory;
	}

	public void setJtHistory(Long jtHistory) {
		this.jtHistory = jtHistory;
	}

	public Long getMedia() {
		return media;
	}

	public void setMedia(Long media) {
		this.media = media;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public List<String> getUrl() {
		return url;
	}

	public void setUrl(List<String> url) {
		this.url = url;
	}
}
