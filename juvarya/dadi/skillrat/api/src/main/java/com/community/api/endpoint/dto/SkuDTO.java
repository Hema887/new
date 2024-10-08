package com.community.api.endpoint.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.util.xml.ISO8601DateAdapter;

import com.broadleafcommerce.rest.api.wrapper.DimensionWrapper;
import com.broadleafcommerce.rest.api.wrapper.WeightWrapper;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkuDTO {
	@XmlElement
	protected Long id;

	@XmlElement
	@XmlJavaTypeAdapter(ISO8601DateAdapter.class)
	protected Date activeStartDate;

	@XmlElement
	@XmlJavaTypeAdapter(ISO8601DateAdapter.class)
	protected Date activeEndDate;

	@XmlElement
	protected String name;

	@XmlElement
	protected Boolean active;

	@XmlElement
	protected Boolean available;

	@XmlElement
	protected String inventoryType;

	@XmlElement
	protected String description;

	@XmlElement
	protected Integer quantityAvailable;

	@XmlElement
	protected Money retailPrice;

	@XmlElement
	protected Money salePrice;

	@XmlElement
	protected WeightWrapper weight;

	@XmlElement
	protected DimensionWrapper dimension;

	@XmlElement
	protected Character archived;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getActiveStartDate() {
		return activeStartDate;
	}

	public void setActiveStartDate(Date activeStartDate) {
		this.activeStartDate = activeStartDate;
	}

	public Date getActiveEndDate() {
		return activeEndDate;
	}

	public void setActiveEndDate(Date activeEndDate) {
		this.activeEndDate = activeEndDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public String getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(String inventoryType) {
		this.inventoryType = inventoryType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQuantityAvailable() {
		return quantityAvailable;
	}

	public void setQuantityAvailable(Integer quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}

	public Money getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(Money retailPrice) {
		this.retailPrice = retailPrice;
	}

	public Money getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Money salePrice) {
		this.salePrice = salePrice;
	}

	public WeightWrapper getWeight() {
		return weight;
	}

	public void setWeight(WeightWrapper weight) {
		this.weight = weight;
	}

	public DimensionWrapper getDimension() {
		return dimension;
	}

	public void setDimension(DimensionWrapper dimension) {
		this.dimension = dimension;
	}

	public Character getArchived() {
		return archived;
	}

	public void setArchived(Character archived) {
		this.archived = archived;
	}

}
