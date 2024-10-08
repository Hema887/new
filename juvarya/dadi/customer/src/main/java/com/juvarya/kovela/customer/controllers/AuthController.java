package com.juvarya.kovela.customer.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.juvarya.kovela.commonservices.dto.JTMediaDTO;
import com.juvarya.kovela.commonservices.dto.JTNotificationListDTO;
import com.juvarya.kovela.commonservices.dto.LoggedInUser;
import com.juvarya.kovela.customer.model.JTCustomerLastLoginModel;
import com.juvarya.kovela.customer.model.JTUserOTPModel;
import com.juvarya.kovela.customer.model.Role;
import com.juvarya.kovela.customer.model.User;
import com.juvarya.kovela.customer.repository.RoleRepository;
import com.juvarya.kovela.customer.repository.UserRepository;
import com.juvarya.kovela.customer.request.LoginRequest;
import com.juvarya.kovela.customer.response.JwtResponse;
import com.juvarya.kovela.customer.response.MessageResponse;
import com.juvarya.kovela.customer.service.JTCustomerLastLoginService;
import com.juvarya.kovela.customer.service.JTCustomerService;
import com.juvarya.kovela.customer.service.JTPostalCodeService;
import com.juvarya.kovela.customer.service.JTUserOTPService;
import com.juvarya.kovela.customer.service.JTZodiacSignService;
import com.juvarya.kovela.customer.service.UserDetailsServiceImpl;
import com.juvarya.kovela.feedservices.JTFeedIntegrationService;
import com.juvarya.kovela.feedservices.impl.JTFeedIntegrationServiceImpl;
import com.juvarya.kovela.model.ERole;
import com.juvarya.kovela.security.jwt.JwtUtils;
import com.juvarya.kovela.security.services.CustomerState;
import com.juvarya.kovela.utils.JTConstants;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private static final String ERROR_ROLE_IS_NOT_FOUND = "Error: Role is not found.";
	private static final String AUTHORIZATION = "Authorization";
	private static final String SIGN_IN = "SIGNIN";
	private static final long OTP_VALID_DURATION = 15 * 60 * 1000;

	@Autowired
	private JTUserOTPService jtUserOTPService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Resource(name = "jtCustomerService")
	private JTCustomerService jtCustomerService;

	@Resource(name = "customerState")
	private CustomerState customerState;

	@Resource(name = "jtZodiacSignService")
	private JTZodiacSignService zodiacSignService;

	@Resource(name = "jtPostalCodeService")
	private JTPostalCodeService jtPostalCodeService;

	@Resource(name = "jtCustomerLastLoginService")
	private JTCustomerLastLoginService customerLastLoginService;

	@CrossOrigin(origins = "*")
	@PostMapping("/signin")
	public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
			throws JsonProcessingException {

		if (null != loginRequest.getPrimaryContact() && !loginRequest.getPrimaryContact().isEmpty()) {

			JTUserOTPModel jtUserOTPModel = jtUserOTPService.findByOtpTypeAndPrimaryContact(SIGN_IN,
					loginRequest.getPrimaryContact());
			if (!Objects.nonNull(jtUserOTPModel)) {
				return ResponseEntity.badRequest().body(new MessageResponse("Unable to find otp"));
			} else if (validateOtpTime(jtUserOTPModel)) {
				return ResponseEntity.badRequest().body(new MessageResponse("Otp expired"));
			}

			if (Objects.isNull(jtUserOTPModel) || !loginRequest.getOtp().equals(jtUserOTPModel.getOtp())
					|| !loginRequest.getPrimaryContact().equals(jtUserOTPModel.getPrimaryContact())) {
				throw new BadCredentialsException("Invalid OTP");

			} else {
				jtUserOTPService.deleteOTP(jtUserOTPModel);
			}
			User user = userDetailsService.loadUserByPrimaryContact(loginRequest.getPrimaryContact());
			if (Objects.isNull(user)) {
				user = new User();
				user.setPrimaryContact(loginRequest.getPrimaryContact());
				Set<Role> userRoles = new HashSet<>();
				userRoles.add(roleRepository.findByName(ERole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException(ERROR_ROLE_IS_NOT_FOUND)));
				user.setRoles(userRoles);
				user.setCreationTime(new Date());
				userRepository.save(user);
				LoggedInUser loggedInUser = convertToLoggedInUser(user);
				String jwt = jwtUtils.generateJwtToken(loggedInUser);
				String refreshToken = jwtUtils.generateRefreshToken(loggedInUser);

				List<String> roles = user.getRoles().stream().map(role -> role.getName().name())
						.collect(Collectors.toList());
				return ResponseEntity.ok(new JwtResponse(jwt, loggedInUser.getId(), loggedInUser.getPrimaryContact(),
						loggedInUser.getEmail(), roles, refreshToken));

			} else if (Objects.nonNull(user)) {
				LoggedInUser loggedInUser = convertToLoggedInUser(user);

				String jwt = jwtUtils.generateJwtToken(loggedInUser);
				String refreshToken = jwtUtils.generateRefreshToken(loggedInUser);

				List<String> roles = user.getRoles().stream().map(role -> role.getName().name())
						.collect(Collectors.toList());
				return ResponseEntity.ok(new JwtResponse(jwt, loggedInUser.getId(), loggedInUser.getPrimaryContact(),
						loggedInUser.getEmail(), roles, refreshToken));
			}

		}
		return ResponseEntity.badRequest().body(new MessageResponse("Required PrimaryContact"));
	}

	private LoggedInUser convertToLoggedInUser(User user) {
		LoggedInUser loggedInUser = new LoggedInUser();
		loggedInUser.setEmail(user.getEmail());
		loggedInUser.setId(user.getId());
		loggedInUser.setPrimaryContact(user.getPrimaryContact());
		loggedInUser.setFullName(user.getFullName());
		List<String> roles = user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toList());
		loggedInUser.setRoles(new HashSet<>(roles));
		return loggedInUser;
	}

	@SuppressWarnings("null")
	@GetMapping("/currentCustomer")
	public ResponseEntity<LoggedInUser> getCurrentCustomer(HttpServletRequest request)
			throws JsonProcessingException, ParseException {
		String jwt = parseJwt(request);
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			LoggedInUser loggedInUser = jwtUtils.getUserFromToken(jwt);
			Optional<User> user = userRepository.findById(loggedInUser.getId());

			JTCustomerLastLoginModel customerLastLoginModel = new JTCustomerLastLoginModel();

			JTCustomerLastLoginModel customerLastLogin = customerLastLoginService.findByJtCustomer(user.get());

			if (Objects.isNull(customerLastLogin) && null == customerLastLogin) {
				Date date = currentDate();
				customerLastLoginModel.setDate(date);
				customerLastLoginModel.setJtCustomer(user.get());
				customerLastLoginService.save(customerLastLoginModel);
			} else if (Objects.nonNull(customerLastLogin) && null != customerLastLogin) {
				Date date = currentDate();
				customerLastLogin.setDate(date);
				customerLastLogin.setJtCustomer(user.get());
				customerLastLoginService.save(customerLastLogin);
			}

			if (user.isPresent()) {
				loggedInUser.setNewUser(Boolean.TRUE);

				if (null != user.get().getFullName()) {
					loggedInUser.setNewUser(Boolean.FALSE);
					loggedInUser.setFullName(user.get().getFullName());
				}

				if (null != user.get().getDateOfBirth()) {
					loggedInUser.setDob(user.get().getDateOfBirth());
				}

				if (null != user.get().getGender()) {
					loggedInUser.setGender(user.get().getGender());
				}

				if (null != user.get().getGothra()) {
					loggedInUser.setGothra(user.get().getGothra());
				}

				if (null != user.get().getWhatsAppEnabled()) {
					loggedInUser.setWhatsAppEnabled(user.get().getWhatsAppEnabled());
				}

				if (null != user.get().getPostalCode()) {
					loggedInUser.setPostalCode(user.get().getPostalCode().getCode());
				}

				if (null != user.get().getEmail()) {
					loggedInUser.setNewUser(Boolean.FALSE);
					loggedInUser.setEmail(user.get().getEmail());
				}

				JTFeedIntegrationService feedIntegrationService = new JTFeedIntegrationServiceImpl();
				JTNotificationListDTO notificationList = feedIntegrationService
						.getNotificationsByType(JTConstants.MEMBERSHIP, CustomerState.currentUser.get());
				if (null != notificationList && !CollectionUtils.isEmpty(notificationList.getNotifications())) {
					loggedInUser.setJtNotifications(notificationList);
				}

				JTMediaDTO customerPicture = feedIntegrationService.getCustomerPicture(user.get().getPrimaryContact(),
						CustomerState.currentUser.get());
				if (null != customerPicture) {
					loggedInUser.setMedia(customerPicture);
				}
			}
			return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
		}
		return null;
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader(AUTHORIZATION);
		if (StringUtils.isNoneEmpty(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}
		return null;
	}

	private Date currentDate() throws ParseException {
		LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
		String formattedTime = localDateTime.format(formatter);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = dateFormat.parse(formattedTime);
		return date;
	}

	private boolean validateOtpTime(JTUserOTPModel userOtpModel) {
		long currentTimeInMillis = System.currentTimeMillis();
		long otpCreationTimeInMillis = userOtpModel.getCreationTime().getTime();
		if (otpCreationTimeInMillis + OTP_VALID_DURATION > currentTimeInMillis) {
			return false;
		}
		return true;
	}

}
