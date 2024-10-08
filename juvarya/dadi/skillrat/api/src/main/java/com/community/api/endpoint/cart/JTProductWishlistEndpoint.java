package com.community.api.endpoint.cart;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.broadleafcommerce.profile.web.core.CustomerState;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.community.api.endpoint.dto.JTProductDTO;
import com.community.api.endpoint.dto.JTProductWishlistDTO;
import com.community.api.endpoint.dto.JTProductWishlistResponseDTO;
import com.community.api.jwt.payload.response.MessageResponse;
import com.community.core.catalog.domain.JTCustomer;
import com.community.core.catalog.domain.JTProduct;
import com.community.core.catalog.domain.JTProductWishlist;
import com.community.core.catalog.domain.impl.JTProductWishlistImpl;
import com.community.core.catalog.service.JTProductService;
import com.community.core.catalog.service.JTProductWishlistService;
import com.community.rest.api.wrapper.JTConstants;

@RestController
@RequestMapping(value = "/productWishlist", produces = { MediaType.APPLICATION_JSON_VALUE,
		MediaType.APPLICATION_XML_VALUE })
public class JTProductWishlistEndpoint extends JTBaseEndpoint {

	private static final String AUTHORIZATION = "Authorization";

	@Resource(name = "jtProductWishlistService")
	private JTProductWishlistService jtProductWishlistService;

	@Resource(name = "jtProductService")
	private JTProductService jtProductService;

	@Autowired
	private ModelMapper modelMapper;

	@SuppressWarnings({ "rawtypes" })
	@PostMapping("/create")
	public ResponseEntity saveProductWishlist(@RequestBody JTProductWishlistDTO dto, HttpServletRequest request)
			throws FileNotFoundException, IOException {
		JTCustomer customer = (JTCustomer) CustomerState.getCustomer();
		JTProductWishlistResponseDTO jtProductWishlistDTO = new JTProductWishlistResponseDTO();
		JTProduct product = jtProductService.findById(dto.getProductId());
		if (null == product) {
			jtProductWishlistDTO.setMessage("product is not available");
			return new ResponseEntity<>(jtProductWishlistDTO, HttpStatus.BAD_REQUEST);
		}
		JTProductWishlist model = jtProductWishlistService.findByJtCustomerAndJtProduct(customer.getId(),
				product.getId());
		if (Boolean.TRUE.equals(dto.getIsLike()) && model != null) {
			return ResponseEntity.accepted().body(new MessageResponse("already user liked this product"));
		}
		if (null != dto.getIsLike() && dto.getIsLike() && model == null) {
			JTProductWishlist jtProductWishlistModel = new JTProductWishlistImpl();
			jtProductWishlistModel.setCreatedBy(customer);
			jtProductWishlistModel.setCreationTime(new Date());
			jtProductWishlistModel.setProduct(product);
			jtProductWishlistService.save(jtProductWishlistModel);
			jtProductWishlistDTO.setStatus(Boolean.TRUE);
		} else if (Boolean.FALSE.equals(dto.getIsLike())) {
			JTProductWishlist jtProductWishlist = jtProductWishlistService
					.findByJtCustomerAndJtProduct(customer.getId(), product.getId());
			jtProductWishlistService.deleteProduct(jtProductWishlist);
			jtProductWishlistDTO.setStatus(Boolean.TRUE);
		}
		jtProductWishlistDTO.setTotalCount(jtProductWishlistService.countByJtProductWishList(product.getId()));
		return new ResponseEntity<>(jtProductWishlistDTO, HttpStatus.OK);
	}

	@GetMapping("/likes")
	public JTProductWishlistDTO getProductLikes(@RequestParam Long productId) {
		JTCustomer customer = (JTCustomer) CustomerState.getCustomer();
		JTProductWishlistDTO productWishlistDTO = new JTProductWishlistDTO();
		JTProductWishlist productWishlist = jtProductWishlistService.findByJtCustomerAndJtProduct(customer.getId(),
				productId);
		if (null != productWishlist) {
			productWishlistDTO.setIsLike(Boolean.TRUE);
		} else {
			productWishlistDTO.setIsLike(Boolean.FALSE);
		}
		JTProduct product = jtProductService.findById(productId);
		if (null != product) {
			productWishlistDTO.setCount(jtProductWishlistService.countByJtProductWishList(productId));
		}
		return productWishlistDTO;
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/wishlistCustomers")
	public ResponseEntity getCustomerWishlists(@RequestParam(defaultValue = "20", required = false) int pageNo,
			@RequestParam(defaultValue = "20", required = false) int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		JTCustomer jtCustomer = (JTCustomer) CustomerState.getCustomer();
		Page<JTProductWishlist> wishlists = jtProductWishlistService.wishlistCustomers(jtCustomer.getId(), pageable);
		List<JTProductWishlistDTO> wishlistDTOs = new ArrayList<>();
		Map<String, Object> response = new HashMap<>();

		if (null != wishlists && !CollectionUtils.isEmpty(wishlists.getContent())) {
			for (JTProductWishlist productWishlist : wishlists) {
				JTProductWishlistDTO jtProductWishlistDTO = new JTProductWishlistDTO();
				if (null != productWishlist.getProduct() && null != productWishlist.getProduct().getDefaultSku()) {
					JTProductDTO dto = modelMapper.map(productWishlist.getProduct(), JTProductDTO.class);
					jtProductWishlistDTO.setJtProductDTO(dto);
					wishlistDTOs.add(jtProductWishlistDTO);
				}
			}
			response.put(JTConstants.CURRENT_PAGE, wishlists.getNumber());
			response.put(JTConstants.TOTAL_PAGES, wishlists.getTotalPages());
			response.put(JTConstants.PAGE_NUM, pageNo);
			response.put(JTConstants.PAGE_SIZE, pageSize);
			response.put(JTConstants.PROFILES, wishlistDTOs);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return null;
	}

}
