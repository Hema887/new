package com.community.rest.api.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlElement;

import org.broadleafcommerce.common.rest.api.wrapper.BaseWrapper;

import com.community.core.catalog.domain.impl.MediaModel;

public class MediaWrapper extends BaseWrapper {

	@XmlElement
	private Long id;
	
	@XmlElement
	private String url;
	
	@XmlElement
	private String name;
	
	@XmlElement
	private String description;
	
	
	public MediaModel convertToModel(MediaWrapper wrapper, HttpServletRequest request) {
		MediaModel mediaModel = new MediaModel();
		mediaModel.setId(wrapper.getId());
		mediaModel.setUrl(wrapper.getUrl());
		mediaModel.setName(wrapper.getName());
		mediaModel.setDecsription(wrapper.getDescription());
		return mediaModel;
	}
	
	public void wrapDetails(MediaModel media, HttpServletRequest request) {
		this.id = media.getId();
		this.url = media.getUrl();
		this.name = media.getName();
		this.description = media.getDecsription();
		
				
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
}
