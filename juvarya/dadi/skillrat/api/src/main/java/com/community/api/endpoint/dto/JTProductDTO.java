package com.community.api.endpoint.dto;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTProductDTO {
	private Long id;
	private String manufacturer;
	private String name;
	private String shortDescription;
	private String longDescription;
	private Date startDate;
	private Date endDate;
	private Date slotDate;
	private Integer slotStartTime;
	private Integer slotEndTime;
	private Long itemId;
	private Long fieldId;
	private String productType;
	private String category;
	private List<MultipartFile> files;
	private List<JTMediaDTO> mediaList;
	private JTPriceDTO price;
	private SkuDTO defaultSku;
	private Long referenceId;
	private boolean active;
	private JTStoreDTO jtStoreDTO;
	private boolean popular;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public JTPriceDTO getPrice() {
		return price;
	}

	public void setPrice(JTPriceDTO price) {
		this.price = price;
	}

	public SkuDTO getDefaultSku() {
		return defaultSku;
	}

	public void setDefaultSku(SkuDTO defaultSku) {
		this.defaultSku = defaultSku;
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

	public Date getSlotDate() {
		return slotDate;
	}

	public void setSlotDate(Date slotDate) {
		this.slotDate = slotDate;
	}

	public Integer getSlotStartTime() {
		return slotStartTime;
	}

	public void setSlotStartTime(Integer slotStartTime) {
		this.slotStartTime = slotStartTime;
	}

	public Integer getSlotEndTime() {
		return slotEndTime;
	}

	public void setSlotEndTime(Integer slotEndTime) {
		this.slotEndTime = slotEndTime;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public List<JTMediaDTO> getMediaList() {
		return mediaList;
	}

	public void setMediaList(List<JTMediaDTO> mediaList) {
		this.mediaList = mediaList;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	public JTStoreDTO getJtStoreDTO() {
		return jtStoreDTO;
	}

	public void setJtStoreDTO(JTStoreDTO jtStoreDTO) {
		this.jtStoreDTO = jtStoreDTO;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isPopular() {
		return popular;
	}

	public void setPopular(boolean popular) {
		this.popular = popular;
	}
}