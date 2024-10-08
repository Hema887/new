/*
 * Copyright 2008-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.community.api.endpoint.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang.ObjectUtils;
import org.apache.http.HttpStatus;
import org.broadleafcommerce.common.rest.api.wrapper.MapElementWrapper;
import org.broadleafcommerce.core.inventory.service.InventoryService;
import org.broadleafcommerce.core.inventory.service.type.InventoryType;
import org.broadleafcommerce.core.order.dao.OrderDao;
import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupImpl;
import org.broadleafcommerce.core.order.domain.NullOrderImpl;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.type.OrderStatus;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;
import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.AddressImpl;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerAddress;
import org.broadleafcommerce.profile.core.domain.Phone;
import org.broadleafcommerce.profile.core.domain.PhoneImpl;
import org.broadleafcommerce.profile.core.service.CustomerAddressService;
import org.broadleafcommerce.profile.web.core.CustomerState;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.broadleafcommerce.rest.api.endpoint.order.CartEndpoint;
import com.broadleafcommerce.rest.api.exception.BroadleafWebServicesException;
import com.broadleafcommerce.rest.api.wrapper.AddressWrapper;
import com.broadleafcommerce.rest.api.wrapper.OrderItemWrapper;
import com.broadleafcommerce.rest.api.wrapper.OrderWrapper;
import com.community.api.jwt.payload.response.MessageResponse;
import com.community.core.catalog.domain.JTCustomer;
import com.community.core.catalog.domain.JTProduct;
import com.community.core.catalog.domain.JTProductToMedia;
import com.community.core.catalog.domain.impl.ERole;
import com.community.core.catalog.service.JTProductService;
import com.community.core.catalog.service.JTProductToMediaService;
import com.community.core.catalog.service.JTRoleService;

/**
 * This is a reference REST API endpoint for cart. This can be modified, used as
 * is, or removed. The purpose is to provide an out of the box RESTful cart
 * service implementation, but also to allow the implementor to have fine
 * control over the actual API, URIs, and general JAX-RS annotations.
 * 
 * @author Kelly Tisdell
 *
 */
@RestController
@RequestMapping(value = "/cart", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class JTCustomCartEndpoint extends CartEndpoint {

	@Resource(name = "blCustomerAddressService")
	private CustomerAddressService customerAddressService;

	@Resource(name = "blOrderDao")
	protected OrderDao orderDao;

	@Resource(name = "jtProductToMediaService")
	private JTProductToMediaService jtProductToMediaService;

	@Resource(name = "jtRoleService")
	private JTRoleService jtRoleService;

	@Resource(name = "blInventoryService")
	private InventoryService inventoryService;

	@Resource(name = "jtProductService")
	private JTProductService jtProductService;

	@Override
	@Transactional
	@RequestMapping(value = "", method = RequestMethod.GET)
	public OrderWrapper findCartForCustomer(HttpServletRequest request) {
		Customer customer = CustomerState.getCustomer();

		List<CustomerAddress> addresses = customerAddressService
				.readActiveCustomerAddressesByCustomerId(customer.getId());
		List<FulfillmentGroup> fulfillmentGroups = new ArrayList<>();
		Order cart = null;
		try {
			cart = orderService.findCartForCustomer(customer);
			if (!CollectionUtils.isEmpty(addresses) && Objects.nonNull(cart)) {
				for (CustomerAddress address : addresses) {
					FulfillmentGroup fulfillmentGroup = new FulfillmentGroupImpl();
					for (FulfillmentGroup group : cart.getFulfillmentGroups()) {
						if (!address.getAddress().getId().equals(group.getAddress().getId())) {
							fulfillmentGroup.setAddress(address.getAddress());
							fulfillmentGroup.setOrder(cart);
							fulfillmentGroups.add(fulfillmentGroup);
						}
					}
				}
				cart.setFulfillmentGroups(fulfillmentGroups);
				cart = orderDao.save(cart);

			} else if (!Objects.nonNull(cart)) {
				cart = orderService.createNewCartForCustomer(customer);
				if (!CollectionUtils.isEmpty(addresses)) {
					for (CustomerAddress address : addresses) {
						FulfillmentGroup fulfillmentGroup = new FulfillmentGroupImpl();
						fulfillmentGroup.setAddress(address.getAddress());
						fulfillmentGroup.setOrder(cart);
						fulfillmentGroups.add(fulfillmentGroup);
					}
					cart.setFulfillmentGroups(fulfillmentGroups);
					cart = orderDao.save(cart);
				}
			}

			OrderWrapper wrapper = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
			wrapper.wrapDetails(cart, request);

			return wrapper;
		} catch (Exception e) {
			// if we failed to find the cart, create a new one
			return findCartForCustomer(request);
		}
	}

	@RequestMapping(value = "/{cartId}", method = RequestMethod.GET)
	public OrderWrapper findCartById(HttpServletRequest request, @PathVariable("cartId") Long cartId) {
		validateCartAndCustomer(cartId);
		Order cart = orderService.findOrderById(cartId);

		OrderWrapper wrapper = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
		wrapper.wrapDetails(cart, request);

		if (!CollectionUtils.isEmpty(wrapper.getOrderItems())) {
			// int size = wrapper.getOrderItems().size();
			int index = 0;

			for (OrderItemWrapper itemWrapper : wrapper.getOrderItems()) {
				if (Objects.nonNull(itemWrapper)) {
					List<JTProductToMedia> productImages = jtProductToMediaService
							.getProductImages(itemWrapper.getProductId());
					List<MapElementWrapper> additionalAttributes = new ArrayList<>();
					if (!CollectionUtils.isEmpty(productImages)) {
						for (JTProductToMedia jtProductToMedia : productImages) {
							MapElementWrapper media = new MapElementWrapper();
							media.setValue(jtProductToMedia.getMedia().getUrl());
							additionalAttributes.add(media);
						}
					}
					itemWrapper.setAdditionalAttributes(additionalAttributes);
					wrapper.getOrderItems().set(index, itemWrapper);
					index++;
				}

			}
		}

		return wrapper;
	}

	@PostMapping(value = "/{cartId}/item", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public OrderWrapper addItemToOrder(HttpServletRequest request, @PathVariable("cartId") Long cartId,
			@RequestBody OrderItemWrapper orderItemWrapper,
			@RequestParam(value = "priceOrder", required = false, defaultValue = "true") Boolean priceOrder,
			@RequestParam(value = "isUpdateRequest", required = false, defaultValue = "false") Boolean isUpdateRequest) {

		JTProduct product = jtProductService.findById(orderItemWrapper.getProductId());
		if (Objects.nonNull(product)) {
			boolean isAvailable = inventoryService.isAvailable(product.getDefaultSku(), orderItemWrapper.getQuantity());
			if (Boolean.TRUE.equals(isAvailable)
					|| product.getDefaultSku().getInventoryType().equals(InventoryType.ALWAYS_AVAILABLE)) {
				return super.addItemToOrder(request, cartId, orderItemWrapper, priceOrder, isUpdateRequest);
			}
			throw BroadleafWebServicesException.build(HttpStatus.SC_BAD_REQUEST)
					.addMessage(BroadleafWebServicesException.UPDATE_CART_ERROR);
		}
		throw BroadleafWebServicesException.build(HttpStatus.SC_BAD_REQUEST)
				.addMessage(BroadleafWebServicesException.PRODUCT_NOT_FOUND);
	}

	private Order validateCartAndCustomer(Long cartId) {
		Order cart = orderService.findOrderById(cartId);
		if (cart == null || cart instanceof NullOrderImpl) {
			throw BroadleafWebServicesException.build(HttpStatus.SC_NOT_FOUND)
					.addMessage(BroadleafWebServicesException.CART_NOT_FOUND);
		}
		JTCustomer jtCustomer = (JTCustomer) CustomerState.getCustomer();
		if (jtCustomer != null && ObjectUtils.notEqual(jtCustomer.getId(), cart.getCustomer().getId())
				&& !jtCustomer.getRoles().contains(jtRoleService.findByName(ERole.ROLE_ADMIN))) {
			throw BroadleafWebServicesException.build(HttpStatus.SC_BAD_REQUEST)
					.addMessage(BroadleafWebServicesException.CART_CUSTOMER_MISMATCH);
		}
		return cart;
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("/update/status/{cartId}")
	public ResponseEntity cancelOrder(@PathVariable("cartId") Long cartId,
			@RequestParam(value = "priceOrder", required = false, defaultValue = "true") Boolean priceOrder)
			throws PricingException {
		Order cart = orderService.findOrderById(cartId);
		if (Objects.nonNull(cart) && cart.getStatus().equals(OrderStatus.SUBMITTED)) {
			cart.setStatus(OrderStatus.CANCELLED);
			orderService.save(cart, priceOrder);
			return ResponseEntity.ok(new MessageResponse("Order cancelled seccessfully"));
		}
		return ResponseEntity.badRequest().body(new MessageResponse("Insufficient details"));
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	@PostMapping("/addresstocart")
	public ResponseEntity addAddressToCart(HttpServletRequest request,
			@RequestBody(required = false) AddressWrapper addressWrapper,
			@RequestParam(required = false) Long addressId, @RequestParam(required = false) Boolean active) {
		Customer customer = CustomerState.getCustomer();

		Order cart = orderService.findCartForCustomer(customer);
		if (!Objects.nonNull(cart)) {
			throw BroadleafWebServicesException.build(HttpStatus.SC_BAD_REQUEST)
					.addMessage(BroadleafWebServicesException.CART_NOT_FOUND);
		}

		List<CustomerAddress> addresses = customerAddressService
				.readActiveCustomerAddressesByCustomerId(customer.getId());
		List<FulfillmentGroup> fulfillmentGroups = new ArrayList<>();
		if (CollectionUtils.isEmpty(addresses) && null != addressId) {
			for (CustomerAddress address : addresses) {
				FulfillmentGroup fulfillmentGroup = new FulfillmentGroupImpl();
				if (address.getId().equals(addressId)) {
					Address cartAddress = address.getAddress();
					fulfillmentGroup.setAddress(cartAddress);
					fulfillmentGroup.setOrder(cart);
					fulfillmentGroups.add(fulfillmentGroup);
				}
			}
			cart.setFulfillmentGroups(fulfillmentGroups);
			cart = orderDao.save(cart);
		} else if (Objects.nonNull(addressWrapper)) {
			AddressImpl address = new AddressImpl();
			address.setFirstName(addressWrapper.getFirstName());
			address.setLastName(addressWrapper.getLastName());
			address.setAddressLine1(addressWrapper.getAddressLine1());
			address.setAddressLine2(addressWrapper.getAddressLine2());
			address.setAddressLine3(addressWrapper.getAddressLine3());
			address.setCity(addressWrapper.getCity());
			address.setStateProvinceRegion(addressWrapper.getStateProvinceRegion());
			address.setCounty(addressWrapper.getIsoCountryAlpha2().getName());
			address.setIsoCountrySubdivision(addressWrapper.getIsoCountrySubdivision());

			if (null != addressWrapper.getPhonePrimary().getPhoneNumber()) {
				Phone phone = new PhoneImpl();
				phone.setPhoneNumber(addressWrapper.getPhonePrimary().getPhoneNumber());
				phone.setActive(Boolean.TRUE);
				phone.setDefault(addressWrapper.getPhonePrimary().getIsDefault());
				address.setPhonePrimary(phone);
			}

			address.setPostalCode(addressWrapper.getPostalCode());
			address.setDefault(addressWrapper.getIsDefault());
			if (null != active & Boolean.TRUE.equals(active)) {
				address.setActive(Boolean.TRUE);
			}
			if (null != addressWrapper.getIsBusiness() && Boolean.TRUE.equals(addressWrapper.getIsBusiness())) {
				address.setBusiness(addressWrapper.getIsBusiness());
				address.setCompanyName(addressWrapper.getCompanyName());
			}

			FulfillmentGroup fulfillmentGroup = new FulfillmentGroupImpl();
			fulfillmentGroup.setAddress(address);
			fulfillmentGroup.setOrder(cart);
			fulfillmentGroups.add(fulfillmentGroup);
			cart.setFulfillmentGroups(fulfillmentGroups);
			cart = orderDao.save(cart);
		} else {
			throw BroadleafWebServicesException.build(HttpStatus.SC_BAD_REQUEST)
					.addMessage(BroadleafWebServicesException.UNKNOWN_ERROR);
		}
		OrderWrapper wrapper = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
		wrapper.wrapDetails(cart, request);

		return ResponseEntity.ok().body(wrapper);
	}
}
