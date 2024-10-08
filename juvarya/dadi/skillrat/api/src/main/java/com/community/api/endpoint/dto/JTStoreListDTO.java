package com.community.api.endpoint.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTStoreListDTO {
	private List<JTStoreDTO> stores;

	public List<JTStoreDTO> getStores() {
		return stores;
	}

	public void setStores(List<JTStoreDTO> stores) {
		this.stores = stores;
	}

}
