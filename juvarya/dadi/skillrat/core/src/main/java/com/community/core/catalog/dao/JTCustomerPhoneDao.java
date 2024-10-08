package com.community.core.catalog.dao;

import org.broadleafcommerce.profile.core.dao.CustomerPhoneDao;
import org.broadleafcommerce.profile.core.domain.CustomerPhone;

public interface JTCustomerPhoneDao extends CustomerPhoneDao{
	
	CustomerPhone findByPhone(String contact);

}
