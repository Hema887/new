package com.juvarya.kovela.commonservices.dto;

import java.util.List;

public class JTProfileListDTO {
	private List<JTProfileDTO> profiles;
	private Integer totalPages;
	private Long totalElements;
	private Integer page;
	private Integer pageSize;
	public List<JTProfileDTO> getProfiles() {
		return profiles;
	}
	public void setProfiles(List<JTProfileDTO> profiles) {
		this.profiles = profiles;
	}
	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	public Long getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
