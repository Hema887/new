package com.community.api.endpoint.dto;

import javax.servlet.http.HttpServletRequest;

import org.broadleafcommerce.common.rest.api.wrapper.APIWrapper;
import org.broadleafcommerce.common.rest.api.wrapper.BaseWrapper;

import com.community.core.catalog.domain.Media;
import com.fasterxml.jackson.annotation.JsonInclude;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTMediaDTO extends BaseWrapper implements APIWrapper<Media> {
	private Long id;
	private String url;
	private String name;
	private String decsription;
	private String extension;
	private Long storeId;

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

	public String getDecsription() {
		return decsription;
	}

	public void setDecsription(String decsription) {
		this.decsription = decsription;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	@Override
	public void wrapDetails(Media model, HttpServletRequest request) {
		this.id = model.getId();
		this.url = model.getUrl();
		this.name = model.getName();
		this.decsription = model.getDecsription();
		this.extension = model.getExtension();
		this.storeId = model.getJtStore();

	}

	@Override
	public void wrapSummary(Media model, HttpServletRequest request) {
		this.id = model.getId();
		this.url = model.getUrl();
		this.name = model.getName();
		this.decsription = model.getDecsription();
		this.extension = model.getExtension();
		this.storeId = model.getJtStore();

	}
}
