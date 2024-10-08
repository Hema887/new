package com.community.api.endpoint.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTOfferCodeDTO {

	private String offerCode;
	private Date offerCodeStartDate;
	private Date offerCodeEndDate;
	private Long offerId;

	public String getOfferCode() {
		return offerCode;
	}

	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}

	public Date getOfferCodeStartDate() {
		return offerCodeStartDate;
	}

	public void setOfferCodeStartDate(Date offerCodeStartDate) {
		this.offerCodeStartDate = offerCodeStartDate;
	}

	public Date getOfferCodeEndDate() {
		return offerCodeEndDate;
	}

	public void setOfferCodeEndDate(Date offerCodeEndDate) {
		this.offerCodeEndDate = offerCodeEndDate;
	}

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

}
