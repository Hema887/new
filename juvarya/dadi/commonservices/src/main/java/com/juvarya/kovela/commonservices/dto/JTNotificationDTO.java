package com.juvarya.kovela.commonservices.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTNotificationDTO {

	private Long id;
	private String type;
	private Long sourceId;
	private Long jtProfile;
	private Long jtCustomerId;
	private Boolean read;
	private Long feedId;
	private Long eventId;
	private Long membershipId;
	private JTFeedDTO jtFeedDTO;
	private JTNotificationDTO jtNotificationDTO;
	private JTProfileDTO jtProfileDTO;
	private LoggedInUser jtCustomer;
	private JTProfileMembershipDTO membershipDTO;
	private Date creationTIme;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public Long getJtProfile() {
		return jtProfile;
	}

	public void setJtProfile(Long jtProfile) {
		this.jtProfile = jtProfile;
	}

	public Long getJtCustomerId() {
		return jtCustomerId;
	}

	public void setJtCustomerId(Long jtCustomerId) {
		this.jtCustomerId = jtCustomerId;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	public Long getFeedId() {
		return feedId;
	}

	public void setFeedId(Long feedId) {
		this.feedId = feedId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Long getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(Long membershipId) {
		this.membershipId = membershipId;
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

	public JTProfileDTO getJtProfileDTO() {
		return jtProfileDTO;
	}

	public void setJtProfileDTO(JTProfileDTO jtProfileDTO) {
		this.jtProfileDTO = jtProfileDTO;
	}

	public LoggedInUser getJtCustomer() {
		return jtCustomer;
	}

	public void setJtCustomer(LoggedInUser jtCustomer) {
		this.jtCustomer = jtCustomer;
	}

	public JTProfileMembershipDTO getMembershipDTO() {
		return membershipDTO;
	}

	public void setMembershipDTO(JTProfileMembershipDTO membershipDTO) {
		this.membershipDTO = membershipDTO;
	}

	public Date getCreationTIme() {
		return creationTIme;
	}

	public void setCreationTIme(Date creationTIme) {
		this.creationTIme = creationTIme;
	}

}
