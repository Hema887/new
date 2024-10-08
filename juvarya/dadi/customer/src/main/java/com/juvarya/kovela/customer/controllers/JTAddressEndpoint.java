package com.juvarya.kovela.customer.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juvarya.kovela.commonservices.dto.LoggedInUser;
import com.juvarya.kovela.customer.dto.JTAddressDTO;
import com.juvarya.kovela.customer.dto.MessageDTO;
import com.juvarya.kovela.customer.model.JTAddressModel;
import com.juvarya.kovela.customer.model.JTPostalCodeModel;
import com.juvarya.kovela.customer.model.User;
import com.juvarya.kovela.customer.populator.JTAddressPopulator;
import com.juvarya.kovela.customer.response.MessageResponse;
import com.juvarya.kovela.customer.service.JTAddressService;
import com.juvarya.kovela.customer.service.JTCustomerService;
import com.juvarya.kovela.customer.service.JTPostalCodeService;
import com.juvarya.kovela.model.JTAddressType;
import com.juvarya.kovela.security.services.CustomerState;
import com.juvarya.kovela.utils.JTConstants;
import com.juvarya.kovela.utils.converter.AbstractConverter;
import com.juvarya.kovela.utils.converter.JTBaseEndpoint;

@SuppressWarnings("rawtypes")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/jtAddress")
public class JTAddressEndpoint extends JTBaseEndpoint {
	Logger logger = LoggerFactory.getLogger(JTAddressEndpoint.class);

	@Resource(name = "jtAddressService")
	private JTAddressService addressService;

	@Resource(name = "jtAddressPopulator")
	private JTAddressPopulator addressPopulator;

	@Resource(name = "customerState")
	private CustomerState customerState;

	@Resource(name = "jtCustomerService")
	private JTCustomerService customerService;

	@Resource(name = "jtPostalCodeService")
	private JTPostalCodeService jtPostalCodeService;

	@PostMapping(value = "/save")
	public ResponseEntity saveAddress(HttpServletRequest request, @RequestBody JTAddressDTO address)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		logger.info("Entering Into Save Address API");
		if (null == address.getAddressType() && (!address.getAddressType().equalsIgnoreCase("profile")
				|| !address.getAddressType().equalsIgnoreCase("user")
				|| !address.getAddressType().equalsIgnoreCase("store")) && null != address.getPostalCode()) {
			return ResponseEntity.badRequest().body(new MessageDTO("Address type is invalid"));
		}
		LoggedInUser user = customerState.getLoggedInUser(request);
		User customer = customerService.findById(user.getId());

		if (null != customer) {
			JTAddressModel addressModel = new JTAddressModel();
			addressModel.setContactNumber(address.getContactNumber());
			addressModel.setLine1(address.getLine1());
			addressModel.setLine2(address.getLine2());
			addressModel.setLine3(address.getLine3());
			addressModel.setLocality(address.getLocality());
			addressModel.setCreationTime(new Date());
			addressModel.setUser(customer);
			JTAddressModel defaultAddress = null;
			JTPostalCodeModel postalCode = jtPostalCodeService.getPostalCode(address.getPostalCode());
			if (null != postalCode) {
				addressModel.setPostalCode(postalCode);
			}
			if (address.getAddressType().equalsIgnoreCase("profile")) {
				addressModel.setAddressType(JTAddressType.PROFILE);
				addressModel.setTargetId(address.getProfileId());
				if (address.getDefaultAddress()
						&& null != addressService.findByTargetId(address.getProfileId(), Boolean.TRUE)) {
					defaultAddress = addressService.findByTargetId(address.getProfileId(), Boolean.TRUE);
					defaultAddress.setDefaultAddress(Boolean.FALSE);
					addressService.saveAddress(defaultAddress);
					addressModel.setDefaultAddress(Boolean.TRUE);
				} else if (address.getDefaultAddress()
						&& null == addressService.findByTargetId(address.getProfileId(), Boolean.TRUE)) {
					addressModel.setDefaultAddress(Boolean.TRUE);
				}
			} else if (address.getAddressType().equalsIgnoreCase("user")) {
				addressModel.setAddressType(JTAddressType.USER);
				addressModel.setTargetId(address.getUserId());
				if (address.getDefaultAddress()
						&& null != addressService.findByTargetId(address.getUserId(), Boolean.TRUE)) {
					defaultAddress = addressService.findByTargetId(address.getProfileId(), Boolean.TRUE);
					defaultAddress.setDefaultAddress(Boolean.FALSE);
					addressService.saveAddress(defaultAddress);
					addressModel.setDefaultAddress(Boolean.TRUE);
				} else if (address.getDefaultAddress()
						&& null == addressService.findByTargetId(address.getProfileId(), Boolean.TRUE)) {
					addressModel.setDefaultAddress(Boolean.TRUE);
				}
			} else if (address.getAddressType().equalsIgnoreCase("store")) {
				addressModel.setAddressType(JTAddressType.STORE);
				addressModel.setTargetId(address.getStoreId());
				if (address.getDefaultAddress()
						&& null != addressService.findByTargetId(address.getStoreId(), Boolean.TRUE)) {
					defaultAddress = addressService.findByTargetId(address.getStoreId(), Boolean.TRUE);
					defaultAddress.setDefaultAddress(Boolean.FALSE);
					addressService.saveAddress(defaultAddress);
					addressModel.setDefaultAddress(Boolean.TRUE);
				} else if (address.getDefaultAddress()
						&& null == addressService.findByTargetId(address.getProfileId(), Boolean.TRUE)) {
					addressModel.setDefaultAddress(Boolean.TRUE);
				}
			}
			addressModel.setPostalCode(postalCode);
			addressModel = addressService.saveAddress(addressModel);
			JTAddressDTO dto = (JTAddressDTO) getConverterInstance().convert(addressModel);
			return ResponseEntity.ok().body(dto);
		} else {
			return ResponseEntity.badRequest().body(new MessageDTO("user should login"));
		}
	}

	@GetMapping("/default/{targetId}")
	public ResponseEntity getAddress(@PathVariable("targetId") Long targetId)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		JTAddressModel address = addressService.findByTargetId(targetId, Boolean.TRUE);
		if (!Objects.nonNull(address)) {
			return null;
		}
		JTAddressDTO dto = (JTAddressDTO) getConverterInstance().convert(address);
		return ResponseEntity.ok().body(dto);
	}

	@SuppressWarnings({ "unchecked" })
	private AbstractConverter<JTAddressModel, ?> getConverterInstance() {
		return getConverter(addressPopulator, JTAddressDTO.class.getName());
	}

	@PutMapping("/update")
	public ResponseEntity updateAddress(@RequestBody JTAddressDTO address) {
		logger.info("Entering Into Update Address API");
		JTAddressModel addressModel = addressService.findById(address.getId());
		if (null != addressModel) {
			if (StringUtils.isNotEmpty(address.getContactNumber())) {
				addressModel.setContactNumber(address.getContactNumber());
			}
			if (StringUtils.isNotEmpty(address.getContactNumber())) {
				addressModel.setLine2(address.getContactNumber());
			}
			if (StringUtils.isNotEmpty(address.getLine3())) {
				addressModel.setLine2(address.getLine3());
			}
			if (StringUtils.isNotEmpty(address.getLine1())) {
				addressModel.setLine1(address.getLine1());
			}
			if (null != address.getPostalCode()) {
				JTPostalCodeModel jtPostalCode = jtPostalCodeService.getPostalCode(address.getPostalCode());
				if (!Objects.isNull(jtPostalCode)) {
					addressModel.setPostalCode(jtPostalCode);
				} 
			}
			if (StringUtils.isNotEmpty(address.getLocality())) {
				addressModel.setLocality(address.getLocality());
			}
			addressModel = addressService.saveAddress(addressModel);
			return ResponseEntity.ok().body(new MessageDTO("Address updated successfully"));
		}
		return ResponseEntity.ok().body(new MessageDTO("Address not found"));
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/list")
	public ResponseEntity userAddresses(HttpServletRequest request, @RequestParam int pageNo,
			@RequestParam int pageSize) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		logger.info("Entering Into UserAddresses API");
		LoggedInUser user = customerState.getLoggedInUser(request);
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<JTAddressModel> addressModel = addressService.findByUser(user.getId(), pageable);
		Map<String, Object> response = new HashMap<>();
		response.put(JTConstants.CURRENT_PAGE, addressModel.getNumber());
		response.put(JTConstants.TOTAL_ITEMS, addressModel.getTotalElements());
		response.put(JTConstants.TOTAL_PAGES, addressModel.getTotalPages());
		response.put(JTConstants.PAGE_NUM, pageNo);
		response.put(JTConstants.PAGE_SIZE, pageSize);
		if (!CollectionUtils.isEmpty(addressModel.getContent())) {
			response.put(JTConstants.PROFILES, getListConverterInstance().convertAll(addressModel.getContent()));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	private AbstractConverter getListConverterInstance() {
		return getConverter(addressPopulator, JTAddressDTO.class.getName());
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/nearByTemples")
	public ResponseEntity nearByProfiles(String isoCode, int pageNo, int pageSize)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<JTAddressModel> profiles = addressService.findNearByProfiles(isoCode, JTAddressType.PROFILE, pageable);

		Map<String, Object> response = new HashMap<>();
		response.put(JTConstants.CURRENT_PAGE, profiles.getNumber());
		response.put(JTConstants.TOTAL_ITEMS, profiles.getTotalElements());
		response.put(JTConstants.TOTAL_PAGES, profiles.getTotalPages());
		response.put(JTConstants.PAGE_NUM, pageNo);
		response.put(JTConstants.PAGE_SIZE, pageSize);
		if (!CollectionUtils.isEmpty(profiles.getContent())) {
			List<JTAddressDTO> list = getListConverterInstance().convertAll(profiles.getContent());
			response.put(JTConstants.PROFILES, list);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity deleteAddress(@RequestParam Long addressId) {
		JTAddressModel addressModel = addressService.findById(addressId);
		if (null != addressModel) {
			addressService.deleteAddress(addressModel);
			return ResponseEntity.ok().body(new MessageResponse("Address Deleted"));
		}
		return ResponseEntity.badRequest().body(new MessageResponse("Address Not Found With Given Id"));
	}

}
