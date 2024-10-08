package com.community.api.endpoint.cart;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.AddressImpl;
import org.broadleafcommerce.profile.core.domain.Phone;
import org.broadleafcommerce.profile.core.domain.PhoneImpl;
import org.broadleafcommerce.profile.core.service.AddressService;
import org.broadleafcommerce.profile.core.service.CustomerPhoneService;
import org.broadleafcommerce.profile.core.service.PhoneService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.broadleafcommerce.rest.api.endpoint.BaseEndpoint;
import com.community.api.endpoint.dto.JTAddressDTO;
import com.community.api.endpoint.dto.JTCustomerDTO;
import com.community.api.endpoint.dto.JTMediaDTO;
import com.community.api.endpoint.dto.JTPhoneDTO;
import com.community.api.endpoint.dto.JTStoreDTO;
import com.community.api.endpoint.dto.JTStoreListDTO;
import com.community.api.endpoint.dto.LoggedInUser;
import com.community.api.jwt.payload.response.MessageResponse;
import com.community.api.security.jwt.JwtUtils;
import com.community.core.azure.service.JTAwsBlobService;
import com.community.core.azure.service.JTCustomerRoleService;
import com.community.core.catalog.domain.JTCustomer;
import com.community.core.catalog.domain.JTStore;
import com.community.core.catalog.domain.Media;
import com.community.core.catalog.domain.impl.ERole;
import com.community.core.catalog.domain.impl.JTStoreImpl;
import com.community.core.catalog.service.JTCustomerService;
import com.community.core.catalog.service.JTRoleService;
import com.community.core.catalog.service.JTStoreService;
import com.community.core.catalog.service.MediaService;
import com.community.rest.api.wrapper.JTConstants;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping(value = "/jtstore", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class JTStoreEndpoint extends BaseEndpoint {

	private static final String AUTHORIZATION = "Authorization";

	@Resource(name = "jtStoreService")
	private JTStoreService jtStoreService;

	@Resource(name = "blAddressService")
	private AddressService addressService;

	@Resource(name = "blPhoneService")
	private PhoneService phoneService;

	@Resource(name = "blCustomerPhoneService")
	private CustomerPhoneService customerPhoneService;

	@Resource(name = "jtCustomerService")
	private JTCustomerService jtCustomerService;

	@Resource(name = "jtAwsBlobService")
	private JTAwsBlobService jtAwsBlobService;

	@Autowired
	private ModelMapper modelMapper;

	@Resource(name = "jtCustomerRoleService")
	private JTCustomerRoleService customerRoleService;

	@Resource(name = "mediaService")
	private MediaService mediaService;

	@Autowired
	JwtUtils jwtUtils;

	@Resource(name = "blCustomerState")
	private CustomerState customerState;

	@Resource(name = "jtRoleService")
	private JTRoleService jtRoleService;

	@SuppressWarnings({ "rawtypes" })
	@PostMapping("/save")
	public ResponseEntity createStore(@RequestBody JTStoreDTO jtStoreDTO, HttpServletRequest request)
			throws JsonProcessingException {
		JTStoreImpl jtStore = new JTStoreImpl();
		JTCustomer customer = (JTCustomer) CustomerState.getCustomer();

		JTCustomer jtCustomer = jtCustomerService.findById(customer.getId());

		if (Objects.nonNull(customer) && customer.getRoles().contains(jtRoleService.findByName(ERole.ROLE_ADMIN))) {
			if (null == jtStoreDTO.getContact()) {
				return ResponseEntity.ok().body(new MessageResponse("Customer Contact Is Required"));
			}
			customer = jtCustomerService.findByPrimaryContact(jtStoreDTO.getContact());
			if (Objects.nonNull(customer)
					&& (null != customer.getCustomerType() && customer.getCustomerType().equals(JTConstants.ARTIST))) {
				JTStore store = jtStoreService.customerStore(customer.getId());
				if (Objects.nonNull(store)) {
					return ResponseEntity.ok().body(new MessageResponse("Store Already Registered"));
				}
				jtStore.setJtCustomer(customer);
				jtStore.setActive(Boolean.TRUE);
			} else {
				return ResponseEntity.ok().body(new MessageResponse("Customer Not Registered Or Invalid CustomerType"));
			}

		} else if ((null != jtCustomer.getCustomerType() && jtCustomer.getCustomerType().equals(JTConstants.ARTIST))) {
			JTStore store = jtStoreService.customerStore(customer.getId());
			if (Objects.nonNull(store)) {
				return ResponseEntity.ok().body(new MessageResponse("Store Already Registered"));
			}
			jtStore.setActive(Boolean.FALSE);
			jtStore.setJtCustomer(jtCustomer);
		} else {
			return ResponseEntity.ok().body(new MessageResponse("Invalid CustomerType"));
		}

		jtStore.setId(jtStoreDTO.getId());
		jtStore.setName(jtStoreDTO.getName());
		jtStore.setCreationTime(new Date());
		jtStore.setLatitude(jtStoreDTO.getLatitude());
		jtStore.setLongitude(jtStoreDTO.getLongitude());
		jtStore.setStoreHours(jtStoreDTO.getStoreHours());

		jtStore.setOpen(jtStoreDTO.isOpen());
		jtStore.setStoreNumber(jtStoreDTO.getStoreNumber());

		Address address = new AddressImpl();
		address = saveAddress(jtStoreDTO);
		jtStore.setAddress(address);
		JTStore updateStore = jtStoreService.save(jtStore);
		modelMapper.map(updateStore, JTStoreDTO.class);
		return ResponseEntity.ok().body(new MessageResponse("Store created"));
	}

	private Address saveAddress(JTStoreDTO jtStoreDTO) {
		Address address = new AddressImpl();

		address.setFirstName(jtStoreDTO.getAddressDTO().getFirstName());
		address.setLastName(jtStoreDTO.getAddressDTO().getLastName());
		address.setAddressLine1(jtStoreDTO.getAddressDTO().getAddressLine1());
		address.setAddressLine2(jtStoreDTO.getAddressDTO().getAddressLine2());
		address.setAddressLine3(jtStoreDTO.getAddressDTO().getAddressLine3());
		address.setCity(jtStoreDTO.getAddressDTO().getCity());
		address.setCompanyName(jtStoreDTO.getAddressDTO().getCompanyName());
		address.setBusiness(jtStoreDTO.getAddressDTO().getIsBusiness());
		address.setDefault(jtStoreDTO.getAddressDTO().getIsDefault());
		address.setPostalCode(jtStoreDTO.getAddressDTO().getPostalCode());

		Phone phone = new PhoneImpl();

		if (null != jtStoreDTO.getAddressDTO() && null != jtStoreDTO.getAddressDTO().getPhonePrimary()
				&& null != jtStoreDTO.getAddressDTO().getPhonePrimary().getPhoneNumber()) {
			Phone primary = phoneService.create();
			primary.setActive(Boolean.TRUE);
			primary.setCountryCode("+91");
			primary.setDefault(Boolean.TRUE);
			primary.setPhoneNumber(jtStoreDTO.getAddressDTO().getPhonePrimary().getPhoneNumber());
			phone = phoneService.savePhone(primary);
		}

		address.setPhonePrimary(phone);
		address = addressService.saveAddress(address);
		return address;
	}

	@SuppressWarnings({ "rawtypes", "unlikely-arg-type" })
	@PutMapping("/approve")
	public ResponseEntity approveStore(@RequestBody JTStoreDTO jtStoreDTO, HttpServletRequest request)
			throws JsonProcessingException {
		String jwt = parseJwt(request);
		LoggedInUser loggedInUser = jwtUtils.getUserFromToken(jwt);
		if (null != jtStoreDTO.getId()) {
			JTStore jtStore = jtStoreService.findById(jtStoreDTO.getId());
			if (Objects.isNull(jtStore)) {
				return ResponseEntity.ok().body(new MessageResponse("Store Not Found"));
			}

			if (Boolean.TRUE.equals(jtStore.getActive())) {
				return ResponseEntity.ok().body(new MessageResponse("Store Already Approved"));
			}

			if (loggedInUser.getRoles().contains(jtRoleService.findByName(ERole.ROLE_ADMIN))) {
				jtStore.setActive(Boolean.TRUE);
				jtStoreService.save(jtStore);
				return ResponseEntity.ok().body(new MessageResponse("Store Approved"));
			} else {
				return ResponseEntity.ok().body(new MessageResponse("You Are Not Able To Active Store"));
			}

		}
		return ResponseEntity.ok().body(new MessageResponse("Store Id is Required"));

	}

//	@SuppressWarnings("rawtypes")
//	@PostMapping(value = "/upload/profile", consumes = { "multipart/form-data" })
//	public ResponseEntity uploadProfile(@RequestParam Long storeId, @ModelAttribute MultipartFile file,
//			HttpServletRequest request) throws IOException {
//		LoggedInUser jtCustomer = customerState.getLoggedInUser(request);
//		JTStore store = jtStoreService.findById(storeId);
//		if (Objects.nonNull(store) && store.getJtCustomer().getId().equals(jtCustomer.getId())) {
//			Media media = jtAwsBlobService.uploadFile(file);
//			media.setJtStore(store.getId());
//			mediaService.create(media);
//			return ResponseEntity.ok().body(new MessageResponse("Profile uploaded"));
//		}
//		return ResponseEntity.ok().body(new MessageResponse("Insufficient details"));
//	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/getProfile")
	public ResponseEntity getStorePicture(@RequestParam Long storeId) {
		Media profilePicture = jtStoreService.getStoreProfilePicture(storeId);
		if (Objects.nonNull(profilePicture)) {
			JTMediaDTO details = modelMapper.map(profilePicture, JTMediaDTO.class);
			return new ResponseEntity<>(details, HttpStatus.OK);
		}
		return new ResponseEntity<>(new MessageResponse("Profile picture not found"), HttpStatus.BAD_REQUEST);
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/{id}")
	public ResponseEntity getStoreDetails(@PathVariable("id") Long id) {
		JTStore jtStore = jtStoreService.findById(id);

		JTStoreDTO jtStoreDTO = modelMapper.map(jtStore, JTStoreDTO.class);

		if (null != jtStore.getJtCustomer()) {
			JTCustomerDTO customerDTO = modelMapper.map(jtStore.getJtCustomer(), JTCustomerDTO.class);
			customerDTO.setPassword(null);
			customerDTO.setEmail(null);
			jtStoreDTO.setCustomerDTO(customerDTO);
		}

		if (null != jtStore.getAddress()) {
			JTAddressDTO addressDTO = modelMapper.map(jtStore.getAddress(), JTAddressDTO.class);
			if (null != jtStore.getAddress().getPhonePrimary()) {
				JTPhoneDTO jtPhoneDTO = modelMapper.map(addressDTO, JTPhoneDTO.class);
				addressDTO.setPhonePrimary(jtPhoneDTO);
			}
			jtStoreDTO.setAddressDTO(addressDTO);
		}
		Media profilePicture = jtStoreService.getStoreProfilePicture(jtStore.getId());
		if (Objects.nonNull(profilePicture)) {
			JTMediaDTO details = modelMapper.map(profilePicture, JTMediaDTO.class);
			jtStoreDTO.setProfilePicture(details);
		}

		return new ResponseEntity<>(jtStoreDTO, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/list")
	public ResponseEntity getAllStores(@RequestParam int pageNo, @RequestParam int pageSize, HttpServletRequest request)
			throws JsonProcessingException {
		String jwt = parseJwt(request);
		LoggedInUser loggedInUser = jwtUtils.getUserFromToken(jwt);
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<JTStore> stores = jtStoreService.getAllStores(pageable);

		Map<String, Object> response = new HashMap<>();
		response.put(JTConstants.CURRENT_PAGE, stores.getNumber());
		response.put(JTConstants.TOTAL_ITEMS, stores.getTotalElements());
		response.put(JTConstants.TOTAL_PAGES, stores.getTotalPages());
		response.put(JTConstants.PAGE_NUM, pageNo);
		response.put(JTConstants.PAGE_SIZE, pageSize);

		List<JTStoreDTO> jtStoreDTOs = new ArrayList<>();
		if (Objects.nonNull(loggedInUser) && loggedInUser.getRoles().contains(JTConstants.ROLE_ADMIN)
				&& !CollectionUtils.isEmpty(stores.getContent())) {
			for (JTStore jtStore : stores) {
				JTStoreDTO jtStoreDTO = modelMapper.map(jtStore, JTStoreDTO.class);
				if (null != jtStore.getJtCustomer()) {
					JTCustomerDTO customerDTO = modelMapper.map(jtStore.getJtCustomer(), JTCustomerDTO.class);
					customerDTO.setPassword(null);
					customerDTO.setEmail(null);
					jtStoreDTO.setCustomerDTO(customerDTO);
				}

				if (null != jtStore.getAddress()) {
					JTAddressDTO addressDTO = modelMapper.map(jtStore.getAddress(), JTAddressDTO.class);
					if (null != jtStore.getAddress().getPhonePrimary()) {
						JTPhoneDTO jtPhoneDTO = modelMapper.map(addressDTO, JTPhoneDTO.class);
						addressDTO.setPhonePrimary(jtPhoneDTO);
					}
					jtStoreDTO.setAddressDTO(addressDTO);
				}
				Media profilePicture = jtStoreService.getStoreProfilePicture(jtStore.getId());
				if (Objects.nonNull(profilePicture)) {
					JTMediaDTO details = modelMapper.map(profilePicture, JTMediaDTO.class);
					jtStoreDTO.setProfilePicture(details);
				}
				jtStoreDTOs.add(jtStoreDTO);
			}
			JTStoreListDTO jtStoreListDTO = new JTStoreListDTO();
			jtStoreListDTO.setStores(jtStoreDTOs);
			response.put(JTConstants.PRIMARY, jtStoreListDTO);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return ResponseEntity.ok().body(new MessageResponse("You're not able to view stores"));
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader(AUTHORIZATION);
		if (StringUtils.isNoneEmpty(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}
		return null;
	}

}
