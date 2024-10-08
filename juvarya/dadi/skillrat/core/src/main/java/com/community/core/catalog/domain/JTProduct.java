package com.community.core.catalog.domain;

import org.broadleafcommerce.core.catalog.domain.Product;

public interface JTProduct extends Product {
	public String getProductType();

	public void setProductType(String productType);

	public String getShortDescription();

	public void setShortDescription(String shortDescription);

	public String getLongDescription();

	public void setLongDescription(String longDescription);

	public Boolean getPopular();

	public void setPopular(Boolean popular);

	public JTCustomer getCreatedBy();

	public void setCreatedBy(JTCustomer createdBy);

	public JTStore getJtStore();

	public void setJtStore(JTStore jtStore);

	public boolean isActive();

	public void setActive(boolean active);
}
