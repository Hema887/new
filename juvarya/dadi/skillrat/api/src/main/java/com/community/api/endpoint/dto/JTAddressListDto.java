package com.community.api.endpoint.dto;

import java.util.List;

public class JTAddressListDto {
	private List<JTAddressDTO> addresses;
	private int page;
	private int pageSize;
	public List<JTAddressDTO> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<JTAddressDTO> addresses) {
		this.addresses = addresses;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
