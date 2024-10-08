package com.community.api.endpoint.cart;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpStatus;
import org.broadleafcommerce.core.checkout.service.CheckoutService;
import org.broadleafcommerce.core.checkout.service.exception.CheckoutException;
import org.broadleafcommerce.core.checkout.service.workflow.CheckoutResponse;
import org.broadleafcommerce.core.inventory.service.InventoryService;
import org.broadleafcommerce.core.inventory.service.InventoryUnavailableException;
import org.broadleafcommerce.core.order.domain.DiscreteOrderItem;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.domain.OrderItemPriceDetail;
import org.broadleafcommerce.core.order.service.OrderService;
import org.broadleafcommerce.core.payment.service.OrderPaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.broadleafcommerce.rest.api.endpoint.BaseEndpoint;
import com.broadleafcommerce.rest.api.exception.BroadleafWebServicesException;
import com.broadleafcommerce.rest.api.wrapper.OrderWrapper;
import com.community.core.catalog.domain.JTProduct;
import com.community.core.catalog.service.JTProductService;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@RestController
@RequestMapping("/checkout")
public class OrderCheckoutEndpoint extends BaseEndpoint {

	@Resource(name = "blCheckoutService")
	protected CheckoutService checkoutService;

	@Resource(name = "blOrderService")
	protected OrderService orderService;

	@Resource(name = "blInventoryService")
	private InventoryService inventoryService;

	@Resource(name = "jtProductService")
	private JTProductService jtProductService;

	@Resource(name = "blOrderPaymentService")
	protected OrderPaymentService orderPaymentService;

	@Value("${otp.trigger}")
	private boolean triggerOtp;

	@Value("${key_id}")
	private String keyId;

	@Value("${key_secret}")
	private String keySecret;

	@PostMapping("/{cartId}")
	public OrderWrapper performCheckout(HttpServletRequest request, @PathVariable("cartId") Long cartId)
			throws InventoryUnavailableException, IOException {
		Order cart = orderService.findOrderById(cartId);
		if (cart != null) {
			try {
				if (!CollectionUtils.isEmpty(cart.getOrderItems())) {
					for (OrderItem item : cart.getOrderItems()) {
						if (item instanceof DiscreteOrderItem) {
							DiscreteOrderItem discreteOrderItem = (DiscreteOrderItem) item;
							JTProduct product = jtProductService.findById(discreteOrderItem.getProduct().getId());
							for (OrderItemPriceDetail quntity : item.getOrderItemPriceDetails()) {
								boolean isAvailable = inventoryService.isAvailable(product.getDefaultSku(),
										quntity.getQuantity());
								if (Boolean.FALSE.equals(isAvailable)) {
									throw BroadleafWebServicesException.build(HttpStatus.SC_BAD_REQUEST)
											.addMessage(BroadleafWebServicesException.CHECKOUT_PROCESSING_ERROR);
								}
							}
						}
					}
					CheckoutResponse response = checkoutService.performCheckout(cart);
					Order order = response.getOrder();
					OrderWrapper wrapper = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
					wrapper.wrapDetails(order, request);
					if (null != response && null != response.getOrder() && null != response.getOrder().getOrderNumber()
							&& triggerOtp) {
						triggerSMS(cart.getEmailAddress(), response.getOrder().getOrderNumber());
					}
					return wrapper;

				} else {
					throw BroadleafWebServicesException.build(HttpStatus.SC_BAD_REQUEST)
							.addMessage(BroadleafWebServicesException.CART_ITEM_NOT_FOUND);
				}

			} catch (CheckoutException e) {
				throw BroadleafWebServicesException.build(HttpStatus.SC_INTERNAL_SERVER_ERROR)
						.addMessage(BroadleafWebServicesException.CHECKOUT_PROCESSING_ERROR);
			}
		}

		throw BroadleafWebServicesException.build(HttpStatus.SC_NOT_FOUND)
				.addMessage(BroadleafWebServicesException.CART_NOT_FOUND);

	}

	public boolean triggerSMS(String mobileNumber, String otp) throws IOException {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url("https://api.authkey.io/request?authkey=5784b393579d5d3d&mobile="
				+ mobileNumber + "&country_code=+91&sid=12135&name=Twinkle&otp=" + otp).get().build();
		Response response = client.newCall(request).execute();
		return response.isSuccessful();
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/details/{PaymentId}")
	public ResponseEntity checkPayment(@PathVariable("PaymentId") String paymentId) throws IOException {
		OkHttpClient client = new OkHttpClient();

		String url = "https://api.razorpay.com/v1/payments/" + paymentId;

		Request request = new Request.Builder().url(url).get()
				.header("Authorization", Credentials.basic(keyId, keySecret)).build();

		Response response = client.newCall(request).execute();

		try {
			if (!response.isSuccessful()) {
				throw new IOException("Unexpected code: " + response.code());
			}

			return ResponseEntity.ok(response.body().string());
		} finally {
			response.close();
		}
	}
}
