package com.community.api.endpoint.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTProductWishlistDTO {

	private Long id;
	private Long productId;
	private Long customerId;
	private Boolean isLike;
	private Date creationTime;
	private Long count;
	private JTProductDTO jtProductDTO;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Boolean getIsLike() {
		return isLike;
	}
	public void setIsLike(Boolean isLike) {
		this.isLike = isLike;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public JTProductDTO getJtProductDTO() {
		return jtProductDTO;
	}
	public void setJtProductDTO(JTProductDTO jtProductDTO) {
		this.jtProductDTO = jtProductDTO;
	}
	
}
