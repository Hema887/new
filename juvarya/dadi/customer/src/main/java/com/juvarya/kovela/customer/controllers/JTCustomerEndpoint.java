package com.juvarya.kovela.customer.controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juvarya.kovela.commonservices.dto.JTMediaDTO;
import com.juvarya.kovela.commonservices.dto.LoggedInUser;
import com.juvarya.kovela.customer.dto.BasicUserDetails;
import com.juvarya.kovela.customer.dto.JTUserDTO;
import com.juvarya.kovela.customer.model.JTPostalCodeModel;
import com.juvarya.kovela.customer.model.JTZodiacSignModel;
import com.juvarya.kovela.customer.model.Role;
import com.juvarya.kovela.customer.model.User;
import com.juvarya.kovela.customer.populator.JTPostalCodePopulator;
import com.juvarya.kovela.customer.populator.JTUserPopulator;
import com.juvarya.kovela.customer.repository.RoleRepository;
import com.juvarya.kovela.customer.repository.UserRepository;
import com.juvarya.kovela.customer.request.RoleUpdateDTO;
import com.juvarya.kovela.customer.response.MessageResponse;
import com.juvarya.kovela.customer.service.JTCustomerService;
import com.juvarya.kovela.customer.service.JTPostalCodeService;
import com.juvarya.kovela.customer.service.JTZodiacSignService;
import com.juvarya.kovela.customer.service.UserDetailsServiceImpl;
import com.juvarya.kovela.customerservices.JTCustomerIntegrationService;
import com.juvarya.kovela.customerservices.impl.JTCustomerIntegrationServiceImpl;
import com.juvarya.kovela.model.ERole;
import com.juvarya.kovela.security.services.CustomerState;
import com.juvarya.kovela.utils.JTConstants;
import com.juvarya.kovela.utils.converter.AbstractConverter;
import com.juvarya.kovela.utils.converter.JTBaseEndpoint;

@SuppressWarnings("rawtypes")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
public class JTCustomerEndpoint extends JTBaseEndpoint {
	Logger logger = LoggerFactory.getLogger(JTCustomerEndpoint.class);
	@Autowired
	private RoleRepository roleRepository;

	@Resource(name = "jtCustomerService")
	private JTCustomerService jtCustomerService;

	@Autowired
	private UserRepository userRepository;

	@Resource(name = "jtUserPopulator")
	private JTUserPopulator jtUserPopulator;

	@Resource(name = "customerState")
	private CustomerState customerState;

	@Resource(name = "jtZodiacSignService")
	private JTZodiacSignService zodiacSignService;

	@Resource(name = "jtPostalCodeService")
	private JTPostalCodeService jtPostalCodeService;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Resource(name = "jtPostalCodePopulator")
	private JTPostalCodePopulator jtPostalCodePopulator;

	@PostMapping("/role")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity updateCustomerRole(@RequestBody RoleUpdateDTO roleUpdate) {
		logger.info("Entering Into Update Customer Role API");
		Optional<Role> roleOptional = roleRepository.findByName(ERole.valueOf(roleUpdate.getRole()));
		if (roleOptional.isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Role is not available"));
		}
		Optional<User> customer = jtCustomerService.findByPrimaryContact(roleUpdate.getPrimaryContact());
		User user = customer.get();
		Set<Role> roles = user.getRoles();
		if (!CollectionUtils.isEmpty(roles) && !verifyRole(roleUpdate.getRole(), roles)) {
			roles.add(roleOptional.get());
			user.setRoles(roles);
			user.setType(roleUpdate.getRoleType());
			userRepository.save(user);
			return ResponseEntity.accepted().body(new MessageResponse("Role updated"));
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Role already updated"));
		}
	}

	@GetMapping("/byemail/{email}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public LoggedInUser getCustomerDetails(@PathVariable("email") String emailOrPhone) {
		User user = jtCustomerService.findByEmail(emailOrPhone);
		if (null == user) {
			Optional<User> userWithContact = jtCustomerService.findByPrimaryContact(emailOrPhone);
			if (userWithContact.isPresent()) {
				user = userWithContact.get();
			}
		}
		LoggedInUser loggedInUser = new LoggedInUser();
		loggedInUser.setId(user.getId());
		loggedInUser.setEmail(user.getEmail());
		loggedInUser.setPrimaryContact(user.getPrimaryContact());
		loggedInUser.setFullName(user.getFullName());
		List<String> roles = user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toList());
		loggedInUser.setRoles(new HashSet<>(roles));
		logger.info("Customer Found With Given Email" + " " + emailOrPhone);
		return loggedInUser;
	}

	@GetMapping("/find/{customerId}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public LoggedInUser getCustomer(HttpServletRequest request, @PathVariable("customerId") Long customer) {

		User user = jtCustomerService.findById(customer);
		LoggedInUser loggedInUser = new LoggedInUser();
		loggedInUser.setId(user.getId());
		loggedInUser.setFullName(user.getFullName());
		List<String> roles = user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toList());
		loggedInUser.setRoles(new HashSet<>(roles));

		JTCustomerIntegrationService integration = new JTCustomerIntegrationServiceImpl();
		JTMediaDTO response = integration.getCustomerProfilePicture(user.getPrimaryContact(),
				CustomerState.currentUser.get());
		if (null != response) {
			loggedInUser.setCustomerProfileUrl(response.getUrl());
		}
		logger.info("Customer Found With Given Id" + " " + customer);
		return loggedInUser;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/list/{role}")
	public ResponseEntity<Map<String, Object>> findCustomerByRoles(@PathVariable("role") String role, int page,
			int pageSize) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		if (!StringUtils.hasText(role) && role.equalsIgnoreCase("role_admin")) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		logger.info("Entering Into findCustomerByRoles API");
		Pageable paginationRequest = PageRequest.of(page, pageSize);
		Page<User> users = jtCustomerService.findByRolesContainsIgnoreCase(role, paginationRequest);

		Map<String, Object> response = new HashMap<>();
		response.put(JTConstants.CURRENT_PAGE, users.getNumber());
		response.put(JTConstants.TOTAL_ITEMS, users.getTotalElements());
		response.put(JTConstants.TOTAL_PAGES, users.getTotalPages());
		response.put(JTConstants.PAGE_NUM, page);
		response.put(JTConstants.PAGE_SIZE, pageSize);
		if (!CollectionUtils.isEmpty(users.getContent())) {
			for (User user : users.getContent()) {
				user.setPrimaryContact(null);
				user.setGothra(null);
				user.setPostalCode(null);
			}
			response.put(JTConstants.PROFILES, getConverterInstance().convertAll(users.getContent()));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		logger.error("Users Not Found With Role" + " " + role);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private boolean verifyRole(String newRole, Set<Role> roles) {
		for (Role role : roles) {
			if (role.getName().toString().equalsIgnoreCase(newRole)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings({ "unchecked" })
	private AbstractConverter getConverterInstance() {
		return getConverter(jtUserPopulator, JTUserDTO.class.getName());
	}

	@SuppressWarnings({ "unchecked" })
	private AbstractConverter getConverterInstances() {
		return getConverter(jtPostalCodePopulator, JTPostalCodePopulator.class.getName());
	}

	@SuppressWarnings({ "null" })
	@PutMapping("/userDetails")
	public ResponseEntity basicUserDetails(HttpServletRequest request, @RequestBody BasicUserDetails details)
			throws ParseException {
		logger.info("Entering Into Update Basic User Details API");
		LoggedInUser loggedInUser = customerState.getLoggedInUser(request);

		User user = userDetailsService.loadUserByPrimaryContact(details.getPrimaryContact());
		if (user != null && null != user.getPrimaryContact()
				&& user.getPrimaryContact().equals(loggedInUser.getPrimaryContact())) {

			if (null != details.getEmail() && org.springframework.util.StringUtils.hasText(details.getEmail())) {
				if (!userRepository.existsByEmail(details.getEmail())) {
					user.setEmail(details.getEmail());
				} else {
					return ResponseEntity.ok().body(new MessageResponse("Error: Email Is Already In Use"));
				}
			}

			if (Objects.nonNull(details.getDob()) && null != details.getDob()) {
				Date date = new Date();
				if (details.getDob().after(date)) {
					return ResponseEntity.ok().body(new MessageResponse("Invalid Date"));
				}
				user.setDateOfBirth(details.getDob());
			}
			if (Objects.nonNull(details.getGender()) && StringUtils.hasText(details.getGender())) {
				user.setGender(details.getGender());
			}
			if (Objects.nonNull(details.getGothra()) && StringUtils.hasText(details.getGothra())) {
				user.setGothra(details.getGothra());
			}
			if (null != details.getWhatsAppEnabled()) {
				user.setWhatsAppEnabled(details.getWhatsAppEnabled());
			}

			if (null != details.getPostalCode()) {
				JTPostalCodeModel postalCode = jtPostalCodeService.getPostalCode(details.getPostalCode());
				if (null != postalCode) {
					user.setPostalCode(postalCode);
				}
			}

			if (Objects.nonNull(details.getZodiacSign()) && StringUtils.hasText(details.getZodiacSign())) {
				List<JTZodiacSignModel> signs = zodiacSignService.findAll();
				for (JTZodiacSignModel sign : signs) {
					if (details.getZodiacSign().toLowerCase().equals(sign.getZodiacSign())) {
						user.setZodiacSign(details.getZodiacSign());
					}
				}
			}

			if (Objects.nonNull(details.getFullName())
					&& org.springframework.util.StringUtils.hasText(details.getFullName())) {
				user.setFullName(details.getFullName());
			}

			user = userRepository.save(user);
			return ResponseEntity.ok().body(new MessageResponse("Details Updated"));
		}
		logger.error("User Not Found With Given Id" + " " + loggedInUser.getId());
		return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/list")
	@PreAuthorize(JTConstants.ADMIN)
	public ResponseEntity<Map<String, Object>> getUsersList(@RequestParam int pageNo, @RequestParam int pageSize)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<User> pageUsers = jtCustomerService.getAllUsers(pageable);

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(JTConstants.CURRENT_PAGE, pageUsers.getNumber());
		response.put(JTConstants.TOTAL_ITEMS, pageUsers.getTotalElements());
		response.put(JTConstants.TOTAL_PAGES, pageUsers.getTotalPages());
		response.put(JTConstants.PAGE_NUM, pageNo);
		response.put(JTConstants.PAGE_SIZE, pageSize);

		if (!CollectionUtils.isEmpty(pageUsers.getContent())) {

			List<JTUserDTO> dtos = getConverterInstance().convertAll(pageUsers.getContent());
			for (JTUserDTO user : dtos) {
				user.setJtPostalCodeDTO(null);
			}
			response.put(JTConstants.PROFILES, dtos);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/nearbyartists")
	// @PreAuthorize(JTConstants.ADMIN)
	public ResponseEntity<Map<String, Object>> nearByArtists(@RequestParam(required = false) List<Long> isoCodes,
			@RequestParam String type, @RequestParam(required = false) Long userId, @RequestParam int pageNo,
			@RequestParam int pageSize) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		User user = jtCustomerService.findById(userId);
		Map<String, Object> response = new HashMap<String, Object>();
		if (null != user && null != isoCodes) {
			Page<User> pageUsers = jtCustomerService.findByPostalCodes(isoCodes, type, pageable);

			response.put(JTConstants.CURRENT_PAGE, pageUsers.getNumber());
			response.put(JTConstants.TOTAL_ITEMS, pageUsers.getTotalElements());
			response.put(JTConstants.TOTAL_PAGES, pageUsers.getTotalPages());
			if (!CollectionUtils.isEmpty(pageUsers.getContent())) {
				response.put(JTConstants.PROFILES, getConverterInstance().convertAll(pageUsers.getContent()));
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		} else {
			List<Long> codes = new ArrayList<>();
			Long code = user.getPostalCode().getCode();
			codes.add(code);
			Page<User> pageUsers = jtCustomerService.findByPostalCodes(codes, type, pageable);
			response.put(JTConstants.CURRENT_PAGE, pageUsers.getNumber());
			response.put(JTConstants.TOTAL_ITEMS, pageUsers.getTotalElements());
			response.put(JTConstants.TOTAL_PAGES, pageUsers.getTotalPages());
			if (!CollectionUtils.isEmpty(pageUsers.getContent())) {
				response.put(JTConstants.PROFILES, getConverterInstance().convertAll(pageUsers.getContent()));
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}

		response.put(JTConstants.PAGE_NUM, pageNo);
		response.put(JTConstants.PAGE_SIZE, pageSize);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/contact")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public LoggedInUser getByPrimaryContact(@RequestParam String primaryContact) {
		Optional<User> customer = jtCustomerService.findByPrimaryContact(primaryContact);
		if (customer.isPresent()) {
			User user = customer.get();
			LoggedInUser loggedInUser = new LoggedInUser();
			loggedInUser.setId(user.getId());
			loggedInUser.setFullName(user.getFullName());
			List<String> roles = user.getRoles().stream().map(role -> role.getName().name())
					.collect(Collectors.toList());
			loggedInUser.setRoles(new HashSet<>(roles));
			loggedInUser.setPrimaryContact(user.getPrimaryContact());

			JTCustomerIntegrationService integration = new JTCustomerIntegrationServiceImpl();
			JTMediaDTO response = integration.getCustomerProfilePicture(user.getEmail(),
					CustomerState.currentUser.get());
			if (null != response) {
				loggedInUser.setCustomerProfileUrl(response.getUrl());
			}
			logger.info("Customer Found With Given Id" + " " + customer);
			return loggedInUser;
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	@GetMapping("/artist/details")
	public ResponseEntity artistDetails(@RequestParam Long artistId)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Optional<User> artist = jtCustomerService.findByCustomerAndType(artistId, "ARTIST");
		if (!artist.isEmpty()) {

			JTUserDTO user = (JTUserDTO) getConverterInstance().convert(artist.get());

			if (null != user.getPrimaryContact()) {
				user.setPrimaryContact(null);
			}
			if (null != user.getEmail()) {
				user.setEmail(null);
			}

			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		return null;
	}

}
