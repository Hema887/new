package com.juvarya.kovela.customer.controllers;

import java.util.Date;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juvarya.kovela.commonservices.dto.JTProfileDTO;
import com.juvarya.kovela.commonservices.dto.LoggedInUser;
import com.juvarya.kovela.customer.dto.JTStoreDTO;
import com.juvarya.kovela.customer.model.JTStoreModel;
import com.juvarya.kovela.customer.model.JTStoreType;
import com.juvarya.kovela.customer.model.User;
import com.juvarya.kovela.customer.populator.JTStorePopulator;
import com.juvarya.kovela.customer.response.MessageResponse;
import com.juvarya.kovela.customer.service.JTAddressService;
import com.juvarya.kovela.customer.service.JTCustomerService;
import com.juvarya.kovela.customer.service.JTStoreService;
import com.juvarya.kovela.profileservices.JTProfileIntegrationService;
import com.juvarya.kovela.profileservices.impl.JTProfileIntegrationServiceImpl;
import com.juvarya.kovela.security.services.CustomerState;
import com.juvarya.kovela.utils.converter.AbstractConverter;
import com.juvarya.kovela.utils.converter.JTBaseEndpoint;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/jtStore")
public class JTStoreEndpoint extends JTBaseEndpoint {

	private static final String PUJASTORE = "PUJASTORE";
	private static final String CONVENTIONHALL = "CONVENTIONHALL";
	private static final String ADMIN = "ROLE_ADMIN";

	@Resource(name = "jtStoreService")
	private JTStoreService storeService;

	@Resource(name = "jtAddressService")
	private JTAddressService addressService;

	@Resource(name = "jtStorePopulator")
	private JTStorePopulator storePopulator;

	@Resource(name = "jtCustomerService")
	private JTCustomerService customerService;

	@Resource(name = "customerState")
	private CustomerState customerState;

	@SuppressWarnings("unchecked")
	@PostMapping("/save")
	public ResponseEntity save(HttpServletRequest request, @RequestBody JTStoreDTO dto)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		LoggedInUser user = customerState.getLoggedInUser(request);
		User customer = customerService.findByEmail(user.getEmail());

		JTProfileIntegrationService integrationService = new JTProfileIntegrationServiceImpl();
		JTProfileDTO profileDTO = integrationService.getProfileDetails(dto.getProfileId(),
				CustomerState.currentUser.get());

		JTStoreModel storeModel = new JTStoreModel();
		storeModel.setName(dto.getName());
		storeModel.setContactName(dto.getContactName());
		storeModel.setContactNumber(dto.getContactNumber());
		storeModel.setCreatedBy(customer);
		storeModel.setCreatedDate(new Date());
		storeModel.setActive(dto.getActive());
		storeModel.setProfile(profileDTO.getId());
		if (dto.getStoreType().equalsIgnoreCase(PUJASTORE)) {
			storeModel.setType(JTStoreType.PUJASTORE);
		} else if (dto.getStoreType().equalsIgnoreCase(CONVENTIONHALL)) {
			storeModel.setType(JTStoreType.CONVENTIONHALL);
		}

		storeModel = storeService.save(storeModel);
		JTStoreDTO storeDTO = (JTStoreDTO) getConverterInstance().convert(storeModel);
		return ResponseEntity.ok().body(storeDTO);
	}

	@SuppressWarnings({ "unchecked" })
	private AbstractConverter getConverterInstance() {
		return getConverter(storePopulator, JTStoreDTO.class.getName());
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/find/{id}")
	public ResponseEntity findStore(@PathVariable("id") Long id)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Optional<JTStoreModel> store = storeService.findById(id);
		if (!store.isEmpty()) {
			JTStoreDTO dto = (JTStoreDTO) getConverterInstance().convert(store.get());
			return ResponseEntity.ok().body(dto);
		}
		return ResponseEntity.ok().body(new MessageResponse("store not found"));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity deleteStore(HttpServletRequest request, @PathVariable("id") Long id) {
		LoggedInUser user = customerState.getLoggedInUser(request);

		Optional<JTStoreModel> store = storeService.findById(id);
		if (store.isEmpty()) {
			return ResponseEntity.ok().body(new MessageResponse("store not found"));
		}
		if (user.getId().equals(store.get().getCreatedBy().getId()) || user.getRoles().contains(ADMIN)) {
			storeService.deleteStore(id);
			return ResponseEntity.ok().body(new MessageResponse("Store deleted"));
		}
		return ResponseEntity.ok().body(new MessageResponse("You are not allowed to delete this store"));
	}

	@SuppressWarnings("unchecked")
	@PutMapping("/update")
	public ResponseEntity updateStore(HttpServletRequest request, @RequestBody JTStoreDTO dto)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		LoggedInUser user = customerState.getLoggedInUser(request);

		if (null == dto.getId()) {
			return ResponseEntity.ok().body(new MessageResponse("storeId shouldn't be null"));
		}
		Optional<JTStoreModel> store = storeService.findById(dto.getId());
		if (store.isEmpty()) {
			return ResponseEntity.ok().body(new MessageResponse("store not found"));
		}
		if (user.getId().equals(store.get().getCreatedBy().getId()) || user.getRoles().contains(ADMIN)) {
			store.get().setName(dto.getName());
			store.get().setContactName(dto.getContactName());
			store.get().setContactNumber(dto.getContactNumber());
			store.get().setActive(dto.getActive());

			JTStoreModel storeModel = storeService.save(store.get());
			JTStoreDTO storeDTO = (JTStoreDTO) getConverterInstance().convert(storeModel);
			return ResponseEntity.ok().body(storeDTO);
		}
		return ResponseEntity.ok().body(new MessageResponse("You are not allowed to update this store"));
	}
}
