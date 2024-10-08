package com.community.api.endpoint.cart;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.CategoryProductXref;
import org.broadleafcommerce.core.catalog.domain.Sku;
import org.broadleafcommerce.core.catalog.domain.SkuImpl;
import org.broadleafcommerce.core.catalog.service.CatalogService;
import org.broadleafcommerce.core.inventory.service.type.InventoryType;
import org.broadleafcommerce.profile.web.core.CustomerState;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.broadleafcommerce.rest.api.endpoint.BaseEndpoint;
import com.community.api.endpoint.dto.CategoryProductXrefDTO;
import com.community.api.endpoint.dto.JTAddressDTO;
import com.community.api.endpoint.dto.JTCustomerDTO;
import com.community.api.endpoint.dto.JTMediaDTO;
import com.community.api.endpoint.dto.JTPhoneDTO;
import com.community.api.endpoint.dto.JTPriceDTO;
import com.community.api.endpoint.dto.JTProductDTO;
import com.community.api.endpoint.dto.JTProductListDTO;
import com.community.api.endpoint.dto.JTStoreDTO;
import com.community.api.jwt.payload.response.MessageResponse;
import com.community.core.azure.service.JTAwsBlobService;
import com.community.core.azure.service.JTCustomerRoleService;
import com.community.core.catalog.domain.JTCategory;
import com.community.core.catalog.domain.JTCustomer;
import com.community.core.catalog.domain.JTProduct;
import com.community.core.catalog.domain.JTProductToMedia;
import com.community.core.catalog.domain.JTStore;
import com.community.core.catalog.domain.impl.ERole;
import com.community.core.catalog.domain.impl.JTProductImpl;
import com.community.core.catalog.service.JTCategoryService;
import com.community.core.catalog.service.JTCustomerService;
import com.community.core.catalog.service.JTProductService;
import com.community.core.catalog.service.JTProductToMediaService;
import com.community.core.catalog.service.JTRoleService;
import com.community.core.catalog.service.JTStoreService;
import com.community.core.catalog.service.MediaService;
import com.community.rest.api.wrapper.JTConstants;

@RestController
@RequestMapping(value = "/product", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class ProductEndpoint extends BaseEndpoint {

	private static final int PAGE_NO = 0;
	private static final int PAGE_SIZE = 20;

	@Autowired
	private ModelMapper modelMapper;

	@Resource(name = "jtProductService")
	private JTProductService jtProductService;

	@Resource(name = "blCatalogService")
	private CatalogService blCatalogService;

	@Resource(name = "jtAwsBlobService")
	private JTAwsBlobService jtAwsBlobService;

	@Resource(name = "jtStoreService")
	private JTStoreService jtStoreService;

	@Resource(name = "jtProductToMediaService")
	private JTProductToMediaService jtProductToMediaService;

	@Resource(name = "mediaService")
	private MediaService mediaService;

	@Resource(name = "jtCustomerRoleService")
	private JTCustomerRoleService customerRoleService;

	@Resource(name = "jtCategoryService")
	private JTCategoryService jtCategoryService;

	@Resource(name = "blCustomerState")
	private CustomerState customerState;

	@Resource(name = "jtCustomerService")
	private JTCustomerService jtCustomerService;

	@Resource(name = "jtRoleService")
	private JTRoleService jtRoleService;

	@SuppressWarnings({ "rawtypes" })
	@PostMapping("/create")
	public ResponseEntity saveProduct(@RequestBody JTProductDTO jtProductDTO, HttpServletRequest request)
			throws FileNotFoundException, IOException {
		JTProductImpl product = new JTProductImpl();
		JTCustomer customer = (JTCustomer) CustomerState.getCustomer();

		// JTCustomer jtCustomer = jtCustomerService.findById(customer.getId());

		JTStore jtStore = jtStoreService.customerStore(customer.getId());
		if (Objects.nonNull(jtStore) && Boolean.TRUE.equals(jtStore.getActive())) {

			product.setLongDescription(jtProductDTO.getLongDescription());
			product.setShortDescription(jtProductDTO.getShortDescription());
			product.setCreatedBy(customer);
			product.setProductType(jtProductDTO.getProductType());
			product.setPopular(jtProductDTO.isPopular());

			Sku sku = new SkuImpl();
			sku.setActiveStartDate(jtProductDTO.getDefaultSku().getActiveStartDate());
			sku.setName(jtProductDTO.getDefaultSku().getName());
			sku.setDescription(jtProductDTO.getDefaultSku().getDescription());
			sku.setLongDescription(jtProductDTO.getDefaultSku().getDescription());
			sku.setRetailPrice(jtProductDTO.getDefaultSku().getRetailPrice());
			sku.setSalePrice(jtProductDTO.getDefaultSku().getSalePrice());
			if (!Objects.nonNull(jtProductDTO.getDefaultSku().getInventoryType())) {
				return ResponseEntity.badRequest().body(new MessageResponse("Should add inventory type"));
			}

			final InventoryType inventoryType = InventoryType
					.getInstance(jtProductDTO.getDefaultSku().getInventoryType());

			if (inventoryType.getType().equals("CHECK_QUANTITY")
					&& null == jtProductDTO.getDefaultSku().getQuantityAvailable()) {
				return ResponseEntity.badRequest().body(new MessageResponse("Should add Qunatity"));
			}

			sku.setInventoryType(inventoryType);

			if (null != jtProductDTO.getDefaultSku().getQuantityAvailable()) {
				sku.setQuantityAvailable(jtProductDTO.getDefaultSku().getQuantityAvailable());
			}

			sku.setDefaultProduct(product);
			Sku updatedSku = blCatalogService.saveSku(sku);
			product.setDefaultSku(updatedSku);
			product.setName(jtProductDTO.getName());
			product.setJtStore(jtStore);
			product.setActive(jtProductDTO.isActive());

			List<JTCategory> categories = jtCategoryService.CategorysList(PAGE_NO, PAGE_SIZE);
			if (!categories.isEmpty()) {
				for (JTCategory category : categories) {
					if (category.getName().equalsIgnoreCase(jtProductDTO.getCategory())) {
						product.setCategory((Category) category);
						break;
					}
				}
			}

			jtProductService.saveProduct(product);
			return ResponseEntity.ok().body(new MessageResponse("Product Created"));
		}
		return ResponseEntity.ok().body(new MessageResponse("Store Not Found"));
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/upload/images", consumes = { "multipart/form-data" })
	public ResponseEntity uploadProductPictures(@RequestParam Long productId, @ModelAttribute List<MultipartFile> files,
			HttpServletRequest request) throws FileNotFoundException, IOException {
		JTCustomer customer = (JTCustomer) CustomerState.getCustomer();

		JTCustomer jtCustomer = jtCustomerService.findById(customer.getId());

		JTProduct product = jtProductService.findById(productId);
		if (null != product && product.getCreatedBy().equals(jtCustomer)) {
			jtProductService.upload(productId, files);
			return new ResponseEntity<>(new MessageResponse("images uploaded"), HttpStatus.OK);
		}
		return new ResponseEntity<>(new MessageResponse("You're not able to upload images for this product"),
				HttpStatus.BAD_REQUEST);
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/{id}")
	public ResponseEntity findById(@PathVariable Long id, HttpServletRequest request) {
		JTProduct product = (JTProduct) blCatalogService.findProductById(id);
		if (Objects.isNull(product)) {
			return ResponseEntity.ok().body(new MessageResponse("Product Not Found"));
		}
		JTStoreDTO jtStoreDTO = modelMapper.map(product.getJtStore(), JTStoreDTO.class);
		if (null != product.getJtStore().getJtCustomer()) {
			JTCustomerDTO customerDTO = modelMapper.map(product.getJtStore().getJtCustomer(), JTCustomerDTO.class);
			customerDTO.setPassword(null);
			customerDTO.setEmail(null);
			jtStoreDTO.setCustomerDTO(customerDTO);
		}
		if (null != product.getJtStore().getAddress()) {
			JTAddressDTO addressDTO = modelMapper.map(product.getJtStore().getAddress(), JTAddressDTO.class);
			if (null != product.getJtStore().getAddress().getPhonePrimary()) {
				JTPhoneDTO jtPhoneDTO = modelMapper.map(product.getJtStore().getAddress().getPhonePrimary(),
						JTPhoneDTO.class);
				addressDTO.setPhonePrimary(jtPhoneDTO);
			}
			jtStoreDTO.setAddressDTO(addressDTO);
		}
		JTProductDTO jtProductDTO = modelMapper.map(product, JTProductDTO.class);
		List<JTProductToMedia> jtProductToMedias = jtProductToMediaService.getProductImages(product.getId());
		if (!CollectionUtils.isEmpty(jtProductToMedias)) {
			List<JTMediaDTO> jtMediaDTOs = new ArrayList<>();
			for (JTProductToMedia jtProductToMedia : jtProductToMedias) {
				if (null != jtProductToMedia.getMedia()) {
					JTMediaDTO jtMediaDTO = modelMapper.map(jtProductToMedia.getMedia(), JTMediaDTO.class);
					jtMediaDTOs.add(jtMediaDTO);
				}
			}
			jtProductDTO.setMediaList(jtMediaDTOs);
		}
		jtProductDTO.setJtStoreDTO(jtStoreDTO);
		return new ResponseEntity<>(jtProductDTO, HttpStatus.OK);
	}

	@GetMapping("/search")
	public JTProductListDTO getAllProducts(@RequestParam(required = false) String query, @RequestParam int pageNo,
			@RequestParam int pageSize) {
		List<JTProduct> products = jtProductService.getProductsList(query, pageNo, pageSize);
		List<JTProductDTO> productDtoList = new ArrayList<>();
		for (JTProduct jtProduct : products) {
			JTProductDTO jtProductDto = new JTProductDTO();
			jtProductDto.setId(jtProduct.getId());
			if (null != jtProduct.getDefaultSku()) {
				jtProductDto.setName(jtProduct.getDefaultSku().getName());
				Money retailPrice = jtProduct.getDefaultSku().getRetailPrice();
				Money salePrice = jtProduct.getDefaultSku().getSalePrice();
				JTPriceDTO price = new JTPriceDTO();
				price.setRetailPrice(retailPrice.getAmount());
				price.setSalePrice(salePrice.getAmount());
				if (null != salePrice.getCurrency()) {
					price.setCurrency(salePrice.getCurrency().getCurrencyCode());
				}
				jtProductDto.setPrice(price);
				jtProductDto.setProductType(jtProduct.getProductType());
			}
			productDtoList.add(jtProductDto);
		}
		JTProductListDTO productList = new JTProductListDTO();
		productList.setPage(pageNo);
		productList.setPageSize(pageSize);
		productList.setProducts(productDtoList);
		return productList;
	}

	@GetMapping("/popular/list")
	public JTProductListDTO getPopularProducts(@RequestParam(required = false) Boolean popular,
			@RequestParam int pageNo, @RequestParam int pageSize) {
		List<JTProduct> products = jtProductService.getPopularProducts(popular, pageNo, pageSize);
		List<JTProductDTO> productDtoList = new ArrayList<>();
		for (JTProduct jtProduct : products) {
			JTProductDTO jtProductDto = new JTProductDTO();
			jtProductDto.setId(jtProduct.getId());
			jtProductDto.setActive(jtProduct.isActive());
			jtProductDto.setPopular(jtProduct.getPopular());
			if (null != jtProduct.getDefaultSku()) {
				jtProductDto.setName(jtProduct.getDefaultSku().getName());
				Money retailPrice = jtProduct.getDefaultSku().getRetailPrice();
				Money salePrice = jtProduct.getDefaultSku().getSalePrice();
				JTPriceDTO price = new JTPriceDTO();
				price.setRetailPrice(retailPrice.getAmount());
				price.setSalePrice(salePrice.getAmount());
				if (null != salePrice.getCurrency()) {
					price.setCurrency(salePrice.getCurrency().getCurrencyCode());
				}
				jtProductDto.setPrice(price);
				jtProductDto.setProductType(jtProduct.getProductType());
			}
			List<JTProductToMedia> jtProductToMedias = jtProductToMediaService.getProductImages(jtProduct.getId());
			if (!CollectionUtils.isEmpty(jtProductToMedias)) {
				List<JTMediaDTO> jtMediaDTOs = new ArrayList<>();
				for (JTProductToMedia jtProductToMedia : jtProductToMedias) {
					if (null != jtProductToMedia.getMedia()) {
						JTMediaDTO jtMediaDTO = modelMapper.map(jtProductToMedia.getMedia(), JTMediaDTO.class);
						jtMediaDTOs.add(jtMediaDTO);
					}
				}
				jtProductDto.setMediaList(jtMediaDTOs);
			}
			productDtoList.add(jtProductDto);
		}
		JTProductListDTO productList = new JTProductListDTO();
		productList.setPage(pageNo);
		productList.setPageSize(pageSize);
		productList.setProducts(productDtoList);
		return productList;
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/store/products")
	public ResponseEntity getAllProductsByStore(@RequestParam Long storeId, @RequestParam int pageNo,
			@RequestParam int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<JTProduct> products = jtProductService.getAllProductsByStore(storeId, pageable);

		Map<String, Object> response = new HashMap<>();
		response.put(JTConstants.CURRENT_PAGE, products.getNumber());
		response.put(JTConstants.TOTAL_ITEMS, products.getTotalElements());
		response.put(JTConstants.TOTAL_PAGES, products.getTotalPages());
		response.put(JTConstants.PAGE_NUM, pageNo);
		response.put(JTConstants.PAGE_SIZE, pageSize);

		List<JTProductDTO> productDtoList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(products.getContent())) {
			for (JTProduct jtProduct : products) {
				JTProductDTO jtProductDto = new JTProductDTO();
				JTStoreDTO jtStoreDTO = modelMapper.map(jtProduct.getJtStore(), JTStoreDTO.class);
				if (null != jtProduct.getJtStore().getJtCustomer()) {

					JTCustomerDTO customerDTO = modelMapper.map(jtProduct.getJtStore().getJtCustomer(),
							JTCustomerDTO.class);
					customerDTO.setPassword(null);
					customerDTO.setEmail(null);
					jtStoreDTO.setCustomerDTO(customerDTO);
				}
				if (null != jtProduct.getJtStore().getAddress()) {
					JTAddressDTO addressDTO = modelMapper.map(jtProduct.getJtStore().getAddress(), JTAddressDTO.class);
					if (null != jtProduct.getJtStore().getAddress().getPhonePrimary()) {
						JTPhoneDTO jtPhoneDTO = modelMapper.map(jtProduct.getJtStore().getAddress().getPhonePrimary(),
								JTPhoneDTO.class);
						addressDTO.setPhonePrimary(jtPhoneDTO);
					}
					jtStoreDTO.setAddressDTO(addressDTO);
					jtProductDto = modelMapper.map(jtProduct, JTProductDTO.class);
					jtProductDto.setJtStoreDTO(jtStoreDTO);
				}

				List<JTProductToMedia> jtProductToMedias = jtProductToMediaService.getProductImages(jtProduct.getId());
				if (!CollectionUtils.isEmpty(jtProductToMedias)) {
					List<JTMediaDTO> jtMediaDTOs = new ArrayList<>();
					for (JTProductToMedia jtProductToMedia : jtProductToMedias) {
						if (null != jtProductToMedia.getMedia()) {
							JTMediaDTO jtMediaDTO = modelMapper.map(jtProductToMedia.getMedia(), JTMediaDTO.class);
							jtMediaDTOs.add(jtMediaDTO);
						}
					}
					jtProductDto.setMediaList(jtMediaDTOs);
				}
				productDtoList.add(jtProductDto);
			}
			JTProductListDTO productList = new JTProductListDTO();
			productList.setProducts(productDtoList);
			response.put(JTConstants.PROFILES, productList);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes" })
	@DeleteMapping("/delete")
	public ResponseEntity deleteProduct(@RequestParam Long productId, HttpServletRequest request) {

		JTCustomer customer = (JTCustomer) CustomerState.getCustomer();

		JTCustomer jtCustomer = jtCustomerService.findById(customer.getId());

		JTProduct jtProduct = jtProductService.findById(productId);
		if (Objects.isNull(jtProduct)) {
			return ResponseEntity.ok().body(new MessageResponse("Product Not Found"));
		}
		if (Objects.nonNull(jtCustomer) && jtCustomer.getRoles().contains(jtRoleService.findByName(ERole.ROLE_ADMIN))) {
			List<JTProductToMedia> jtProductToMedias = jtProductToMediaService.getProductImages(jtProduct.getId());
			if (!CollectionUtils.isEmpty(jtProductToMedias)) {
				for (JTProductToMedia jtProductToMedia : jtProductToMedias) {
					mediaService.removeMedia(jtProductToMedia.getMedia());
					jtProductToMediaService.removeMedia(jtProductToMedia);
				}
			}

			if (null != jtProduct.getDefaultSku()) {
				blCatalogService.removeSku(jtProduct.getDefaultSku());
				blCatalogService.removeProduct(jtProduct.getDefaultSku().getDefaultProduct());
			}
			jtProductService.deleteProduct(jtProduct);
			return ResponseEntity.ok().body(new MessageResponse("Product Deleted"));
		}

		return ResponseEntity.ok().body(new MessageResponse("You're not able to delete Product"));
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/products/bycategory")
	public ResponseEntity getProductsByCategory(@RequestParam Long categoryId, @RequestParam int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<CategoryProductXref> products = jtProductService.getProductsByCategory(categoryId, pageable);

		List<CategoryProductXrefDTO> productsByCategory = new ArrayList<>();

		if (null != products && !CollectionUtils.isEmpty(products.getContent())) {
			for (CategoryProductXref categoryProductXref : products) {
				CategoryProductXrefDTO dto = new CategoryProductXrefDTO();
				if (null != categoryProductXref.getProduct()) {
					JTProductDTO jtProductDTO = modelMapper.map(categoryProductXref.getProduct(), JTProductDTO.class);
					List<JTProductToMedia> jtProductToMedias = jtProductToMediaService
							.getProductImages(categoryProductXref.getProduct().getId());
					if (!CollectionUtils.isEmpty(jtProductToMedias)) {
						List<JTMediaDTO> jtMediaDTOs = new ArrayList<>();
						for (JTProductToMedia jtProductToMedia : jtProductToMedias) {
							if (null != jtProductToMedia.getMedia()) {
								JTMediaDTO jtMediaDTO = modelMapper.map(jtProductToMedia.getMedia(), JTMediaDTO.class);
								jtMediaDTOs.add(jtMediaDTO);
							}
						}
						jtProductDTO.setMediaList(jtMediaDTOs);
					}
					dto.setJtProduct(jtProductDTO);
				}
				productsByCategory.add(dto);
			}
			Map<String, Object> response = new HashMap<>();
			response.put(JTConstants.CURRENT_PAGE, products.getNumber());
			response.put(JTConstants.TOTAL_ITEMS, products.getTotalElements());
			response.put(JTConstants.TOTAL_PAGES, products.getTotalPages());
			response.put(JTConstants.PAGE_NUM, pageNo);
			response.put(JTConstants.PAGE_SIZE, pageSize);
			response.put(JTConstants.PROFILES, productsByCategory);
			return ResponseEntity.ok(response);
		}
		return null;

	}

	@SuppressWarnings({ "rawtypes", "null" })
	@PutMapping("/update")
	public ResponseEntity updateProduct(@RequestBody JTProductDTO jtProductDTO, HttpServletRequest request) {

		JTCustomer customer = (JTCustomer) CustomerState.getCustomer();

		JTCustomer jtCustomer = jtCustomerService.findById(customer.getId());

		JTProduct product = (JTProduct) blCatalogService.findProductById(jtProductDTO.getId());

		if (!Objects.nonNull(product) && product.getCreatedBy().equals(jtCustomer)) {
			return ResponseEntity.badRequest().body("insufficient details");
		}

		if (null != jtProductDTO.getLongDescription()) {
			product.setLongDescription(jtProductDTO.getLongDescription());
		}

		if (null != jtProductDTO.getShortDescription()) {
			product.setShortDescription(jtProductDTO.getShortDescription());
		}
		if (null != jtProductDTO.getProductType()) {
			product.setProductType(jtProductDTO.getProductType());
		}

		if (null != product && null != product.getDefaultSku() && null != jtProductDTO.getDefaultSku()) {

			Sku defaultSku = blCatalogService.findSkuById(product.getDefaultSku().getId());
			if (Objects.nonNull(defaultSku)) {

				if (null != jtProductDTO.getDefaultSku().getActiveStartDate()) {
					defaultSku.setActiveStartDate(jtProductDTO.getDefaultSku().getActiveStartDate());
				}

				if (null != jtProductDTO.getDefaultSku().getName()) {
					defaultSku.setName(jtProductDTO.getDefaultSku().getName());
				}

				if (null != jtProductDTO.getDefaultSku().getDescription()) {
					defaultSku.setDescription(jtProductDTO.getDefaultSku().getDescription());
				}

				if (null != jtProductDTO.getDefaultSku().getRetailPrice()) {
					defaultSku.setRetailPrice(jtProductDTO.getDefaultSku().getRetailPrice());
				}

				if (null != jtProductDTO.getDefaultSku().getSalePrice()) {
					defaultSku.setSalePrice(jtProductDTO.getDefaultSku().getSalePrice());
				}
				if (jtProductDTO.getDefaultSku().getInventoryType().equals("ALWAYS_AVAILABLE")) {
					defaultSku.setInventoryType(InventoryType.ALWAYS_AVAILABLE);
				} else if (jtProductDTO.getDefaultSku().getInventoryType().equals("CHECK_QUANTITY")) {
					defaultSku.setInventoryType(InventoryType.CHECK_QUANTITY);
				}
				blCatalogService.saveSku(defaultSku);
			}
		}
		if (jtProductDTO.isActive()) {
			product.setActive(jtProductDTO.isActive());
		}

		List<JTCategory> categories = jtCategoryService.CategorysList(PAGE_NO, PAGE_SIZE);
		if (!categories.isEmpty()) {
			for (JTCategory category : categories) {
				if (null != jtProductDTO.getCategory()) {
					if (category.getName().equalsIgnoreCase(jtProductDTO.getCategory())) {
						CategoryProductXref categoryProductXref = jtProductService.findByProduct(product.getId());
						categoryProductXref.setCategory(category);
						jtProductService.saveCategoryProductXref(categoryProductXref);
						break;
					}
				}
			}
		}

		product = (JTProductImpl) jtProductService.saveProduct(product);

		return ResponseEntity.ok().body(new MessageResponse("Product Updated"));
	}

	
	@SuppressWarnings("rawtypes")
	@GetMapping("/sorting")
	public ResponseEntity getSortedByNameANDPriceANDPopular(@RequestParam(required = false) String name,@RequestParam(required = false) BigDecimal salePrice,@RequestParam(required = false) Boolean popular,@RequestParam int pageNo, int pageSize ) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<JTProduct> products=jtProductService.findSortedByNameANDPriceANDPopular(name, salePrice, popular, pageable);
		List<JTProductDTO> productDtoList = new ArrayList<>();
		for (JTProduct jtProduct : products) {
			JTProductDTO jtProductDto = new JTProductDTO();
			if (null != jtProduct.getDefaultSku()  && null !=jtProduct.getDefaultSku().getName()) {
				jtProductDto.setName(jtProduct.getDefaultSku().getName());
				Money retailPrice = jtProduct.getDefaultSku().getRetailPrice();
				Money productPrice = jtProduct.getDefaultSku().getSalePrice();
				JTPriceDTO price = new JTPriceDTO();
				price.setRetailPrice(retailPrice.getAmount());
				price.setSalePrice(productPrice.getAmount());
				if (null != productPrice.getCurrency()) {
					price.setCurrency(productPrice.getCurrency().getCurrencyCode());
				}
				jtProductDto.setPrice(price);
				jtProductDto.setProductType(jtProduct.getProductType());
			}
			List<JTProductToMedia> jtProductToMedias = jtProductToMediaService.getProductImages(jtProduct.getId());
			if (!CollectionUtils.isEmpty(jtProductToMedias)) {
				List<JTMediaDTO> jtMediaDTOs = new ArrayList<>();
				for (JTProductToMedia jtProductToMedia : jtProductToMedias) {
					if (null != jtProductToMedia.getMedia()) {
						JTMediaDTO jtMediaDTO = modelMapper.map(jtProductToMedia.getMedia(), JTMediaDTO.class);
						jtMediaDTOs.add(jtMediaDTO);
					}
				}
				jtProductDto.setMediaList(jtMediaDTOs);
			}
			productDtoList.add(jtProductDto);
		}
		JTProductListDTO productList = new JTProductListDTO();
		productList.setPage(pageNo);
		productList.setPageSize(pageSize);
		productList.setProducts(productDtoList);
		return new ResponseEntity<>(productList, HttpStatus.OK);
	}
}