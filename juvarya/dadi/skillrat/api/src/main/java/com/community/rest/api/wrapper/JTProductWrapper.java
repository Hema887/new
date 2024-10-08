package com.community.rest.api.wrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.collections4.CollectionUtils;
import org.broadleafcommerce.common.file.service.StaticAssetPathService;
import org.broadleafcommerce.common.media.domain.Media;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.rest.api.wrapper.APIWrapper;
import org.broadleafcommerce.common.rest.api.wrapper.BaseWrapper;
import org.broadleafcommerce.common.util.xml.ISO8601DateAdapter;
import org.broadleafcommerce.core.catalog.domain.ProductOption;
import org.broadleafcommerce.core.catalog.domain.Sku;
import org.springframework.core.env.Environment;
import com.broadleafcommerce.rest.api.wrapper.DynamicSkuPricesWrapper;
import com.broadleafcommerce.rest.api.wrapper.MediaWrapper;
import com.broadleafcommerce.rest.api.wrapper.ProductAttributeWrapper;
import com.broadleafcommerce.rest.api.wrapper.ProductOptionWrapper;
import com.broadleafcommerce.rest.api.wrapper.PromotionMessageDTOWrapper;
import com.broadleafcommerce.rest.api.wrapper.RelatedProductWrapper;
import com.broadleafcommerce.rest.api.wrapper.SkuBundleItemWrapper;
import com.broadleafcommerce.rest.api.wrapper.SkuWrapper;
import com.community.core.catalog.domain.impl.JTProductImpl;

public class JTProductWrapper extends BaseWrapper implements APIWrapper<JTProductImpl> {

	private static final long serialVersionUID = 1L;

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
	protected SkuWrapper defaultSku;

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

	public JTProductImpl convertToModel(JTProductWrapper wrapper, HttpServletRequest request) {
		JTProductImpl skillratProduct = new JTProductImpl();
		skillratProduct.setId(wrapper.getId());
		skillratProduct.setLongDescription(wrapper.getLongDescription());
		skillratProduct.setShortDescription(wrapper.getDescription());
		skillratProduct.setProductType(wrapper.getProductType());
		return skillratProduct;
	}

	@Override
	public void wrapDetails(JTProductImpl model, HttpServletRequest request) {
		this.id = model.getId();
		this.longDescription = model.getLongDescription();
		this.active = model.isActive();
		this.productType = model.getProductType();
		this.setDescription(model.getShortDescription());
	}

	protected Boolean shouldIncludePromotionMessages(HttpServletRequest request) {
		if (request.getParameter("includePromotionMessages") != null) {
			return Boolean.valueOf(request.getParameter("includePromotionMessages"));
		}

		return context.getBean(Environment.class).getProperty("product.wrapper.include.promotion.messages",
				Boolean.class);
	}

	protected Boolean shouldIncludePriceData(HttpServletRequest request) {
		if (request.getParameter("includePriceData") != null) {
			return Boolean.valueOf(request.getParameter("includePriceData"));
		}

		return context.getBean(Environment.class).getProperty("product.wrapper.include.price.data", Boolean.class);
	}

	@Override
	public void wrapSummary(JTProductImpl model, HttpServletRequest request) {
		this.id = model.getId();
		this.name = model.getName();
		this.description = model.getDescription();
		this.longDescription = model.getLongDescription();
		this.url = model.getUrl();
		this.active = model.isActive();
		this.productType = model.getProductType();

		if (model.getDefaultSku() != null) {
			SkuWrapper skuWrapper = (SkuWrapper) context.getBean(SkuWrapper.class.getName());
			skuWrapper.wrapSummary(model.getDefaultSku(), request);
			this.defaultSku = skuWrapper;
		}

		if (CollectionUtils.isNotEmpty(model.getAdditionalSkus())) {
			List<SkuWrapper> additionalSkus = new ArrayList<>();
			for (Sku sku : model.getAdditionalSkus()) {
				SkuWrapper skuWrapper = (SkuWrapper) context.getBean(SkuWrapper.class.getName());
				skuWrapper.wrapSummary(sku, request);
				additionalSkus.add(skuWrapper);
			}
			this.additionalSkus = additionalSkus;
		}

		if (model.getProductOptions() != null && !model.getProductOptions().isEmpty()) {
			this.productOptions = new ArrayList<ProductOptionWrapper>();
			List<ProductOption> options = model.getProductOptions();
			for (ProductOption option : options) {
				ProductOptionWrapper optionWrapper = (ProductOptionWrapper) context
						.getBean(ProductOptionWrapper.class.getName());
				optionWrapper.wrapSummary(option, request);
				this.productOptions.add(optionWrapper);
			}
		}

		if (model.getMedia() != null && !model.getMedia().isEmpty()) {
			Media media = model.getMedia().get("primary");
			if (media != null) {
				StaticAssetPathService staticAssetPathService = (StaticAssetPathService) this.context
						.getBean("blStaticAssetPathService");
				primaryMedia = (MediaWrapper) context.getBean(MediaWrapper.class.getName());
				primaryMedia.wrapDetails(media, request);
				if (primaryMedia.isAllowOverrideUrl()) {
					primaryMedia.setUrl(staticAssetPathService.convertAssetPath(media.getUrl(),
							request.getContextPath(), request.isSecure()));
				}
			}
		}
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the longDescription
	 */
	public String getLongDescription() {
		return longDescription;
	}

	/**
	 * @param longDescription the longDescription to set
	 */
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the retailPrice
	 */
	public Money getRetailPrice() {
		return retailPrice;
	}

	/**
	 * @param retailPrice the retailPrice to set
	 */
	public void setRetailPrice(Money retailPrice) {
		this.retailPrice = retailPrice;
	}

	/**
	 * @return the salePrice
	 */
	public Money getSalePrice() {
		return salePrice;
	}

	/**
	 * @param salePrice the salePrice to set
	 */
	public void setSalePrice(Money salePrice) {
		this.salePrice = salePrice;
	}

	/**
	 * @return the primaryMedia
	 */
	public MediaWrapper getPrimaryMedia() {
		return primaryMedia;
	}

	/**
	 * @param primaryMedia the primaryMedia to set
	 */
	public void setPrimaryMedia(MediaWrapper primaryMedia) {
		this.primaryMedia = primaryMedia;
	}

	/**
	 * @return the dynamicSkuPrices
	 */
	public DynamicSkuPricesWrapper getDynamicSkuPrices() {
		return dynamicSkuPrices;
	}

	/**
	 * @param dynamicSkuPrices the dynamicSkuPrices to set
	 */
	public void setDynamicSkuPrices(DynamicSkuPricesWrapper dynamicSkuPrices) {
		this.dynamicSkuPrices = dynamicSkuPrices;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	public SkuWrapper getDefaultSku() {
		return defaultSku;
	}

	public void setDefaultSku(SkuWrapper defaultSku) {
		this.defaultSku = defaultSku;
	}

	public List<SkuWrapper> getAdditionalSkus() {
		return additionalSkus;
	}

	public void setAdditionalSkus(List<SkuWrapper> additionalSkus) {
		this.additionalSkus = additionalSkus;
	}

	/**
	 * @return the productOptions
	 */
	public List<ProductOptionWrapper> getProductOptions() {
		return productOptions;
	}

	/**
	 * @param productOptions the productOptions to set
	 */
	public void setProductOptions(List<ProductOptionWrapper> productOptions) {
		this.productOptions = productOptions;
	}

	/**
	 * @return the priority
	 */
	public Integer getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	/**
	 * @return the bundleItemsRetailPrice
	 */
	public Money getBundleItemsRetailPrice() {
		return bundleItemsRetailPrice;
	}

	/**
	 * @param bundleItemsRetailPrice the bundleItemsRetailPrice to set
	 */
	public void setBundleItemsRetailPrice(Money bundleItemsRetailPrice) {
		this.bundleItemsRetailPrice = bundleItemsRetailPrice;
	}

	/**
	 * @return the bundleItemsSalePrice
	 */
	public Money getBundleItemsSalePrice() {
		return bundleItemsSalePrice;
	}

	/**
	 * @param bundleItemsSalePrice the bundleItemsSalePrice to set
	 */
	public void setBundleItemsSalePrice(Money bundleItemsSalePrice) {
		this.bundleItemsSalePrice = bundleItemsSalePrice;
	}

	/**
	 * @return the activeStartDate
	 */
	public Date getActiveStartDate() {
		return activeStartDate;
	}

	/**
	 * @param activeStartDate the activeStartDate to set
	 */
	public void setActiveStartDate(Date activeStartDate) {
		this.activeStartDate = activeStartDate;
	}

	/**
	 * @return the activeEndDate
	 */
	public Date getActiveEndDate() {
		return activeEndDate;
	}

	/**
	 * @param activeEndDate the activeEndDate to set
	 */
	public void setActiveEndDate(Date activeEndDate) {
		this.activeEndDate = activeEndDate;
	}

	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the promoMessage
	 */
	public String getPromoMessage() {
		return promoMessage;
	}

	/**
	 * @param promoMessage the promoMessage to set
	 */
	public void setPromoMessage(String promoMessage) {
		this.promoMessage = promoMessage;
	}

	/**
	 * @return the defaultCategoryId
	 */
	public Long getDefaultCategoryId() {
		return defaultCategoryId;
	}

	/**
	 * @param defaultCategoryId the defaultCategoryId to set
	 */
	public void setDefaultCategoryId(Long defaultCategoryId) {
		this.defaultCategoryId = defaultCategoryId;
	}

	/**
	 * @return the upsaleProducts
	 */
	public List<RelatedProductWrapper> getUpsaleProducts() {
		return upsaleProducts;
	}

	/**
	 * @param upsaleProducts the upsaleProducts to set
	 */
	public void setUpsaleProducts(List<RelatedProductWrapper> upsaleProducts) {
		this.upsaleProducts = upsaleProducts;
	}

	/**
	 * @return the crossSaleProducts
	 */
	public List<RelatedProductWrapper> getCrossSaleProducts() {
		return crossSaleProducts;
	}

	/**
	 * @param crossSaleProducts the crossSaleProducts to set
	 */
	public void setCrossSaleProducts(List<RelatedProductWrapper> crossSaleProducts) {
		this.crossSaleProducts = crossSaleProducts;
	}

	/**
	 * @return the productAttributes
	 */
	public List<ProductAttributeWrapper> getProductAttributes() {
		return productAttributes;
	}

	/**
	 * @param productAttributes the productAttributes to set
	 */
	public void setProductAttributes(List<ProductAttributeWrapper> productAttributes) {
		this.productAttributes = productAttributes;
	}

	/**
	 * @return the media
	 */
	public List<MediaWrapper> getMedia() {
		return media;
	}

	/**
	 * @param media the media to set
	 */
	public void setMedia(List<MediaWrapper> media) {
		this.media = media;
	}

	/**
	 * @return the skuBundleItems
	 */
	public List<SkuBundleItemWrapper> getSkuBundleItems() {
		return skuBundleItems;
	}

	/**
	 * @param skuBundleItems the skuBundleItems to set
	 */
	public void setSkuBundleItems(List<SkuBundleItemWrapper> skuBundleItems) {
		this.skuBundleItems = skuBundleItems;
	}

	/**
	 * @return the promotionMessages
	 */
	public Map<String, List<PromotionMessageDTOWrapper>> getPromotionMessages() {
		return promotionMessages;
	}

	/**
	 * @param promotionMessages the promotionMessages to set
	 */
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
