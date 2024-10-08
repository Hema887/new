package com.community.core.catalog.domain;

import java.util.Date;

public interface JTCategoryToMedia {

	public Long getId();

	public void setId(Long id);

	public Long getCategory();

	public void setCategory(Long category);

	public Media getMedia();

	public void setMedia(Media media);

	public Date getCreationTime();

	public void setCreationTime(Date creationTime);
}
