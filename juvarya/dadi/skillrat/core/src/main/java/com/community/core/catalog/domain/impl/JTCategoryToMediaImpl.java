package com.community.core.catalog.domain.impl;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.community.core.catalog.domain.JTCategoryToMedia;
import com.community.core.catalog.domain.Media;

@Entity
@Table(name = "JTCATEGORY_TO_MEDIA", indexes = {
		@Index(name = "idx_jtcategorytomediaid", columnList = "id", unique = true) })
public class JTCategoryToMediaImpl implements JTCategoryToMedia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "JTCATEGORY")
	private Long category;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = MediaModel.class, optional = true)
	@JoinColumn(name = "MEDIA")
	private Media media;

	@Column(name = "CREATION_TIME")
	private Date creationTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
}
