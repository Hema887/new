package com.community.core.catalog.domain;

import java.util.Date;

public interface JTProductToMedia {

	public Long getId();

	public void setId(Long id);

	public Long getProduct();

	public void setProduct(Long product);

	public Media getMedia();

	public void setMedia(Media media);

	public Date getCreationTime();

	public void setCreationTime(Date creationTime);
}
