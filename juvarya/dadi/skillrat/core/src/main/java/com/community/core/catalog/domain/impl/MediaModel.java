package com.community.core.catalog.domain.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.community.core.catalog.domain.Media;

@Entity
@Table(name = "MEDIA_MODEL")
public class MediaModel implements Media {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "URL", length = Integer.MAX_VALUE)
	private String url;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESSCRIPTION")
	private String decsription;

	@Column(name = "EXTENSION")
	private String extension;

	@Column(name = "JT_STORE")
	private Long jtStore;

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

	public Long getJtStore() {
		return jtStore;
	}

	public void setJtStore(Long jtStore) {
		this.jtStore = jtStore;
	}

}
