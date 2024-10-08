package com.community.core.catalog.dao;

import org.broadleafcommerce.core.order.dao.OrderDao;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.type.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JTOrderDao extends OrderDao {

	Page<Order> findAllOrders(OrderStatus status, Pageable pageable);
}
