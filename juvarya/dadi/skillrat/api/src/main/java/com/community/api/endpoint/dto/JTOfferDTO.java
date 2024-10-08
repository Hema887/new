package com.community.api.endpoint.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTOfferDTO {

	@XmlElement
	private Long offerId;

	@XmlElement
	private Date startDate;

	@XmlElement
	private Date endDate;

	@XmlElement
	private String marketingMessage;

	@XmlElement
	private String description;

	@XmlElement
	private String name;

	@XmlElement
	private Boolean automatic;

	@XmlElement
	private String offerType;

	@XmlElement
	private String discountType;

	@XmlElement
	private Character archived;

	private BigDecimal value;

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getMarketingMessage() {
		return marketingMessage;
	}

	public void setMarketingMessage(String marketingMessage) {
		this.marketingMessage = marketingMessage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getAutomatic() {
		return automatic;
	}

	public void setAutomatic(Boolean automatic) {
		this.automatic = automatic;
	}

	public String getOfferType() {
		return offerType;
	}

	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public Character getArchived() {
		return archived;
	}

	public void setArchived(Character archived) {
		this.archived = archived;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
