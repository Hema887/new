package com.juvarya.kovela.commonservices.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTNotificationListDTO {
	private List<JTNotificationDTO> notifications;

	public List<JTNotificationDTO> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<JTNotificationDTO> notifications) {
		this.notifications = notifications;
	}

}
