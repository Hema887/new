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

import com.community.core.catalog.domain.JTProductToMedia;
import com.community.core.catalog.domain.Media;

@Entity
@Table(name = "JTPRODUCT_TO_MEDIA", indexes = {
		@Index(name = "idx_productomediaid", columnList = "id", unique = true) })
public class JTProductToMediaImpl implements JTProductToMedia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "JTPRODUCT")
	private Long product;

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

	public Long getProduct() {
		return product;
	}

	public void setProduct(Long product) {
		this.product = product;
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
