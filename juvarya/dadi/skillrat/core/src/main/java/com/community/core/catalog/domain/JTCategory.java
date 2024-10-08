package com.community.core.catalog.domain;

import org.broadleafcommerce.core.catalog.domain.Category;

public interface JTCategory extends Category {

	Boolean getTopLevelCategory();

	void setTopLevelCategory(Boolean topLevelCategory);

}
