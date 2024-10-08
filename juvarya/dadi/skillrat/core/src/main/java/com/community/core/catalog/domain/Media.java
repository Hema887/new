package com.community.core.catalog.domain;

public interface Media {
	public Long getId();

	public void setId(Long id);

	public String getUrl();

	public void setUrl(String url);

	public String getName();

	public void setName(String name);

	public String getDecsription();

	public void setDecsription(String decsription);

	public Long getJtStore();

	public void setJtStore(Long jtStore);
	
	public String getExtension();
	
	public void setExtension(String extension);
}
