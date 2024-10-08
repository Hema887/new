package com.juvarya.kovela.commonservices.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTFeedReviewDTO {

	private Long id;
	private Long jtCustomer;
	private Date creationTime;
	private Long jtFeedId;
	private Boolean like;
	private Long count;
	private JTNotificationDTO jtNotificationDTO;
	private JTFeedDTO jtFeedDTO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getJtCustomer() {
		return jtCustomer;
	}

	public void setJtCustomer(Long jtCustomer) {
		this.jtCustomer = jtCustomer;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Long getJtFeedId() {
		return jtFeedId;
	}

	public void setJtFeedId(Long jtFeedId) {
		this.jtFeedId = jtFeedId;
	}

	public Boolean getLike() {
		return like;
	}

	public void setLike(Boolean like) {
		this.like = like;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public JTFeedDTO getJtFeedDTO() {
		return jtFeedDTO;
	}

	public void setJtFeedDTO(JTFeedDTO jtFeedDTO) {
		this.jtFeedDTO = jtFeedDTO;
	}

	public JTNotificationDTO getJtNotificationDTO() {
		return jtNotificationDTO;
	}

	public void setJtNotificationDTO(JTNotificationDTO jtNotificationDTO) {
		this.jtNotificationDTO = jtNotificationDTO;
	}

}
