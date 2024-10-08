package com.community.api.endpoint.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTCategoryDTO {

	@XmlElement
	private Long id;

	@XmlElement
	private String name;

	@XmlElement
	private Boolean topLevelCategory;

	@XmlElement
	private List<MultipartFile> files;

	@XmlElement
	private int categoryOrder;

	@XmlElement
	private boolean active;

	@XmlElement
	private List<String> media;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getTopLevelCategory() {
		return topLevelCategory;
	}

	public void setTopLevelCategory(Boolean topLevelCategory) {
		this.topLevelCategory = topLevelCategory;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public int getCategoryOrder() {
		return categoryOrder;
	}

	public void setCategoryOrder(int categoryOrder) {
		this.categoryOrder = categoryOrder;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<String> getMedia() {
		return media;
	}

	public void setMedia(List<String> media) {
		this.media = media;
	}

}