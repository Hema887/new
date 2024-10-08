package com.community.api.endpoint.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.util.xml.ISO8601DateAdapter;

import com.broadleafcommerce.rest.api.wrapper.DynamicSkuPricesWrapper;
import com.broadleafcommerce.rest.api.wrapper.MediaWrapper;
import com.broadleafcommerce.rest.api.wrapper.ProductAttributeWrapper;
import com.broadleafcommerce.rest.api.wrapper.ProductOptionWrapper;
import com.broadleafcommerce.rest.api.wrapper.PromotionMessageDTOWrapper;
import com.broadleafcommerce.rest.api.wrapper.RelatedProductWrapper;
import com.broadleafcommerce.rest.api.wrapper.SkuBundleItemWrapper;
import com.broadleafcommerce.rest.api.wrapper.SkuWrapper;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
	@XmlElement
	protected Long id;

	@XmlElement
	protected String name;

	@XmlElement
	protected String description;

	@XmlElement
	protected String longDescription;

	@XmlElement
	protected String url;

	@XmlElement
	protected Money retailPrice;

	@XmlElement
	protected Money salePrice;

	@XmlElement
	protected MediaWrapper primaryMedia;

	@XmlElement
	protected DynamicSkuPricesWrapper dynamicSkuPrices;

	@XmlElement
	protected Boolean active;

	@XmlElement(name = "productOption")
	@XmlElementWrapper(name = "productOptions")
	protected List<ProductOptionWrapper> productOptions;

	// For bundles
	@XmlElement
	protected Integer priority;

	@XmlElement
	protected Money bundleItemsRetailPrice;

	@XmlElement
	protected Money bundleItemsSalePrice;

	// End for bundles

	@XmlElement
	@XmlJavaTypeAdapter(ISO8601DateAdapter.class)
	protected Date activeStartDate;

	@XmlElement
	@XmlJavaTypeAdapter(ISO8601DateAdapter.class)
	protected Date activeEndDate;

	@XmlElement
	protected String manufacturer;

	@XmlElement
	protected String model;

	@XmlElement
	protected String promoMessage;

	@XmlElement
	protected Long defaultSku;

	@XmlElement
	protected List<SkuWrapper> additionalSkus;

	@XmlElement
	protected Long defaultCategoryId;

	@XmlElement(name = "upsaleProduct")
	@XmlElementWrapper(name = "upsaleProducts")
	protected List<RelatedProductWrapper> upsaleProducts;

	@XmlElement(name = "crossSaleProduct")
	@XmlElementWrapper(name = "crossSaleProducts")
	protected List<RelatedProductWrapper> crossSaleProducts;

	@XmlElement(name = "productAttribute")
	@XmlElementWrapper(name = "productAttributes")
	protected List<ProductAttributeWrapper> productAttributes;

	@XmlElement(name = "media")
	@XmlElementWrapper(name = "mediaItems")
	protected List<MediaWrapper> media;

	@XmlElement(name = "skuBundleItem")
	@XmlElementWrapper(name = "skuBundleItems")
	protected List<SkuBundleItemWrapper> skuBundleItems;

	@XmlElement(name = "promotionMessages")
	@XmlElementWrapper(name = "promotionMessages")
	protected Map<String, List<PromotionMessageDTOWrapper>> promotionMessages;

	@XmlElement
	protected Character archived;

	@XmlElement
	private String productType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public MediaWrapper getPrimaryMedia() {
		return primaryMedia;
	}

	public void setPrimaryMedia(MediaWrapper primaryMedia) {
		this.primaryMedia = primaryMedia;
	}

	public DynamicSkuPricesWrapper getDynamicSkuPrices() {
		return dynamicSkuPrices;
	}

	public void setDynamicSkuPrices(DynamicSkuPricesWrapper dynamicSkuPrices) {
		this.dynamicSkuPrices = dynamicSkuPrices;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public List<ProductOptionWrapper> getProductOptions() {
		return productOptions;
	}

	public void setProductOptions(List<ProductOptionWrapper> productOptions) {
		this.productOptions = productOptions;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Money getBundleItemsRetailPrice() {
		return bundleItemsRetailPrice;
	}

	public void setBundleItemsRetailPrice(Money bundleItemsRetailPrice) {
		this.bundleItemsRetailPrice = bundleItemsRetailPrice;
	}

	public Money getBundleItemsSalePrice() {
		return bundleItemsSalePrice;
	}

	public void setBundleItemsSalePrice(Money bundleItemsSalePrice) {
		this.bundleItemsSalePrice = bundleItemsSalePrice;
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

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getPromoMessage() {
		return promoMessage;
	}

	public void setPromoMessage(String promoMessage) {
		this.promoMessage = promoMessage;
	}

	public Long getDefaultSku() {
		return defaultSku;
	}

	public void setDefaultSku(Long defaultSku) {
		this.defaultSku = defaultSku;
	}

	public List<SkuWrapper> getAdditionalSkus() {
		return additionalSkus;
	}

	public void setAdditionalSkus(List<SkuWrapper> additionalSkus) {
		this.additionalSkus = additionalSkus;
	}

	public Long getDefaultCategoryId() {
		return defaultCategoryId;
	}

	public void setDefaultCategoryId(Long defaultCategoryId) {
		this.defaultCategoryId = defaultCategoryId;
	}

	public List<RelatedProductWrapper> getUpsaleProducts() {
		return upsaleProducts;
	}

	public void setUpsaleProducts(List<RelatedProductWrapper> upsaleProducts) {
		this.upsaleProducts = upsaleProducts;
	}

	public List<RelatedProductWrapper> getCrossSaleProducts() {
		return crossSaleProducts;
	}

	public void setCrossSaleProducts(List<RelatedProductWrapper> crossSaleProducts) {
		this.crossSaleProducts = crossSaleProducts;
	}

	public List<ProductAttributeWrapper> getProductAttributes() {
		return productAttributes;
	}

	public void setProductAttributes(List<ProductAttributeWrapper> productAttributes) {
		this.productAttributes = productAttributes;
	}

	public List<MediaWrapper> getMedia() {
		return media;
	}

	public void setMedia(List<MediaWrapper> media) {
		this.media = media;
	}

	public List<SkuBundleItemWrapper> getSkuBundleItems() {
		return skuBundleItems;
	}

	public void setSkuBundleItems(List<SkuBundleItemWrapper> skuBundleItems) {
		this.skuBundleItems = skuBundleItems;
	}

	public Map<String, List<PromotionMessageDTOWrapper>> getPromotionMessages() {
		return promotionMessages;
	}

	public void setPromotionMessages(Map<String, List<PromotionMessageDTOWrapper>> promotionMessages) {
		this.promotionMessages = promotionMessages;
	}

	public Character getArchived() {
		return archived;
	}

	public void setArchived(Character archived) {
		this.archived = archived;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
}
