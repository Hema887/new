package com.community.api.endpoint.cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpStatus;
import org.broadleafcommerce.common.rest.api.wrapper.MapElementWrapper;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.type.OrderStatus;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.web.core.CustomerState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.broadleafcommerce.rest.api.endpoint.order.OrderHistoryEndpoint;
import com.broadleafcommerce.rest.api.exception.BroadleafWebServicesException;
import com.broadleafcommerce.rest.api.exception.InvalidEnumerationValueException;
import com.broadleafcommerce.rest.api.wrapper.OrderItemWrapper;
import com.broadleafcommerce.rest.api.wrapper.OrderWrapper;
import com.community.api.jwt.payload.response.MessageResponse;
import com.community.core.catalog.dao.JTOrderDao;
import com.community.core.catalog.domain.JTCustomer;
import com.community.core.catalog.domain.JTProductToMedia;
import com.community.core.catalog.domain.JTRole;
import com.community.core.catalog.domain.impl.ERole;
import com.community.core.catalog.service.JTCustomerService;
import com.community.core.catalog.service.JTProductToMediaService;
import com.community.core.catalog.service.JTRoleService;
import com.community.rest.api.wrapper.JTConstants;

@RestController
@RequestMapping(value = "/orders", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class JTOrderHistoryEndpoint extends OrderHistoryEndpoint {

	@Resource(name = "jtProductToMediaService")
	private JTProductToMediaService jtProductToMediaService;

	@Resource(name = "jtOrderDao")
	private JTOrderDao jtOrderDao;

	@Resource(name = "jtCustomerService")
	private JTCustomerService jtCustomerService;

	@Resource(name = "jtRoleService")
	private JTRoleService jtRoleService;

	@GetMapping("")
	public List<OrderWrapper> findOrdersForCustomer(HttpServletRequest request,
			@RequestParam(value = "orderStatus", defaultValue = "SUBMITTED") String orderStatus,
			@RequestParam(value = "orderNumber", required = false) String orderNumber) {
		Customer customer = CustomerState.getCustomer();
		OrderStatus status = OrderStatus.getInstance(orderStatus);
		if (status == null) {
			throw new InvalidEnumerationValueException("Invalid OrderStatus");
		}
		if (customer != null) {
			if (orderNumber == null) {
				List<Order> orders = orderService.findOrdersForCustomer(customer, status);

				if (!CollectionUtils.isEmpty(orders)) {
					List<OrderWrapper> wrappers = new ArrayList<>();
					for (Order order : orders) {
						OrderWrapper wrapper = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
						wrapper.wrapSummary(order, request);
						if (!CollectionUtils.isEmpty(wrapper.getOrderItems())) {
							int index = 0;
							for (OrderItemWrapper itemWrapper : wrapper.getOrderItems()) {
								if (Objects.nonNull(itemWrapper)) {
									List<MapElementWrapper> media = populateMeidia(itemWrapper.getProductId());
									itemWrapper.setAdditionalAttributes(media);
								}
								wrapper.getOrderItems().set(index, itemWrapper);
								index++;
							}
						}
						wrappers.add(wrapper);
					}

					return wrappers;
				} else {
					return new ArrayList<>();
				}
			} else {
				List<OrderWrapper> wrappers = new ArrayList<>();
				OrderWrapper orderWrapper = findOrderByOrderNumber(request, orderNumber);
				if (!CollectionUtils.isEmpty(orderWrapper.getOrderItems())) {
					int index = 0;
					for (OrderItemWrapper itemWrapper : orderWrapper.getOrderItems()) {
						if (Objects.nonNull(itemWrapper)) {
							List<MapElementWrapper> media = populateMeidia(itemWrapper.getProductId());
							itemWrapper.setAdditionalAttributes(media);
						}
						orderWrapper.getOrderItems().set(index, itemWrapper);
						index++;
					}
				}
				wrappers.add(orderWrapper);
				return wrappers;
			}
		}

		throw BroadleafWebServicesException.build(HttpStatus.SC_BAD_REQUEST)
				.addMessage(BroadleafWebServicesException.CUSTOMER_NOT_FOUND);
	}

	private List<MapElementWrapper> populateMeidia(Long productId) {
		List<JTProductToMedia> productImages = jtProductToMediaService.getProductImages(productId);
		List<MapElementWrapper> additionalAttributes = new ArrayList<>();
		if (!CollectionUtils.isEmpty(productImages)) {
			for (JTProductToMedia jtProductToMedia : productImages) {
				MapElementWrapper media = new MapElementWrapper();
				media.setValue(jtProductToMedia.getMedia().getUrl());
				additionalAttributes.add(media);
			}
		}
		return additionalAttributes;
	}

	@SuppressWarnings({ "rawtypes", "unlikely-arg-type" })
	@GetMapping("/list")
	public ResponseEntity findAllOrders(HttpServletRequest request,
			@RequestParam(value = "orderStatus", defaultValue = "SUBMITTED") String orderStatus,
			@RequestParam int pageNo, @RequestParam int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		JTCustomer customer = (JTCustomer) CustomerState.getCustomer();
		JTCustomer jtCustomer = jtCustomerService.findById(customer.getId());
		Set<JTRole> jtRoles = jtCustomer.getRoles();

		if (null != jtCustomer && jtRoles.contains(jtRoleService.findByName(ERole.ROLE_ADMIN))) {

			OrderStatus status = OrderStatus.getInstance(orderStatus);
			if (status == null) {
				throw new InvalidEnumerationValueException("Invalid OrderStatus");
			}

			Page<Order> orders = jtOrderDao.findAllOrders(status, pageable);
			List<OrderWrapper> wrappers = new ArrayList<>();
			if (null != orders && !CollectionUtils.isEmpty(orders.getContent())) {
				for (Order order : orders) {
					OrderWrapper wrapper = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
					wrapper.wrapSummary(order, request);
					if (!CollectionUtils.isEmpty(wrapper.getOrderItems())) {
						int index = 0;
						for (OrderItemWrapper itemWrapper : wrapper.getOrderItems()) {
							if (Objects.nonNull(itemWrapper)) {
								List<MapElementWrapper> media = populateMeidia(itemWrapper.getProductId());
								itemWrapper.setAdditionalAttributes(media);
							}
							wrapper.getOrderItems().set(index, itemWrapper);
							index++;
						}
					}
					wrappers.add(wrapper);
				}
				Map<String, Object> response = new HashMap<>();
				response.put(JTConstants.CURRENT_PAGE, orders.getNumber());
				response.put(JTConstants.TOTAL_ITEMS, orders.getTotalElements());
				response.put(JTConstants.TOTAL_PAGES, orders.getTotalPages());
				response.put(JTConstants.PAGE_NUM, pageNo);
				response.put(JTConstants.PAGE_SIZE, pageSize);
				response.put(JTConstants.PROFILES, wrappers);
				return ResponseEntity.ok(response);
			}
			return null;
		}

		return ResponseEntity.ok().body(new MessageResponse("You Are Not Allowed To View Orders"));

	}
}
