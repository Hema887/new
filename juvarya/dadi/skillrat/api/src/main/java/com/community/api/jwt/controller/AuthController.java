package com.community.api.jwt.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.management.relation.RoleInfoNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.community.api.endpoint.dto.LoggedInUser;
import com.community.api.jwt.payload.request.LoginRequest;
import com.community.api.jwt.payload.response.JwtResponse;
import com.community.api.jwt.payload.response.MessageResponse;
import com.community.api.security.jwt.JwtUtils;
import com.community.core.catalog.dao.JTCustomerLastLoginDao;
import com.community.core.catalog.domain.JTCustomer;
import com.community.core.catalog.domain.JTCustomerLastLogin;
import com.community.core.catalog.domain.JTRole;
import com.community.core.catalog.domain.JTUserOTP;
import com.community.core.catalog.domain.impl.ERole;
import com.community.core.catalog.domain.impl.EType;
import com.community.core.catalog.domain.impl.JTCustomerImpl;
import com.community.core.catalog.domain.impl.JTCustomerLastLoginImpl;
import com.community.core.catalog.service.JTCustomerService;
import com.community.core.catalog.service.JTRoleService;
import com.community.core.catalog.service.JTUserOTPService;
import com.community.rest.api.wrapper.JTConstants;
import com.fasterxml.jackson.core.JsonProcessingException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private static final String ERROR_ROLE_IS_NOT_FOUND = "Error: Role is not found.";
	private static final String MOBILE_NUMBER_PATTERN = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
	private static final String AUTHORIZATION = "Authorization";

	@Autowired
	JwtUtils jwtUtils;

	@Resource(name = "jtUserOTPService")
	private JTUserOTPService jtUserOTPService;

	@Resource(name = "jtCustomerService")
	private JTCustomerService jtCustomerService;

	@Resource(name = "jtRoleService")
	private JTRoleService jtRoleService;

	@Resource(name = "jtCustomerLastLoginDao")
	private JTCustomerLastLoginDao customerLastLoginDao;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
			throws JsonProcessingException, RoleInfoNotFoundException {

		if (null != loginRequest.getPrimaryContact() && !loginRequest.getPrimaryContact().isEmpty()
				&& loginRequest.getPrimaryContact().matches(MOBILE_NUMBER_PATTERN)) {

			JTUserOTP jtUserOTP = jtUserOTPService.findByPrimaryContactAndOtpType(loginRequest.getPrimaryContact(),
					JTConstants.SING_IN);

			if (null == jtUserOTP || !jtUserOTP.getPrimaryContact().equals(loginRequest.getPrimaryContact())
					|| !jtUserOTP.getOtp().equals(loginRequest.getOtp())) {
				return ResponseEntity.ok().body(new MessageResponse("Invalid OTP"));
			}
			jtUserOTPService.delete(jtUserOTP.getId());

			JTCustomer jtCustomer = jtCustomerService.findByPrimaryContact(loginRequest.getPrimaryContact());

			if (null == jtCustomer) {

				jtCustomer = new JTCustomerImpl();

				if (!StringUtils.isEmpty(loginRequest.getPrimaryContact())) {
					String userName = (StringUtils.isNotBlank(loginRequest.getPrimaryContact())
							? loginRequest.getPrimaryContact()
							: "");
					jtCustomer.setUsername(userName);
					jtCustomer.setEmailAddress(userName);
				}
				jtCustomer.setType(EType.STORE);
				Set<JTRole> userRoles = new HashSet<>();
				userRoles.add(jtRoleService.getByName(ERole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException(ERROR_ROLE_IS_NOT_FOUND)));
				jtCustomer.setRoles(userRoles);
				jtCustomer.setCreationTime(new Date());

				jtCustomerService.save(jtCustomer);

				LoggedInUser loggedInUser = convertToLoggedInUser(jtCustomer);

				String jwt = jwtUtils.generateJwtToken(loggedInUser);
				String refreshToken = jwtUtils.generateRefreshToken(loggedInUser);

				List<String> roles = jtCustomer.getRoles().stream().map(role -> role.getName().name())
						.collect(Collectors.toList());
				return ResponseEntity.ok(new JwtResponse(jwt, loggedInUser.getId(), loggedInUser.getPrimaryContact(),
						loggedInUser.getEmail(), roles, refreshToken));
			} else if (null != jtCustomer && Objects.nonNull(jtCustomer)) {
				LoggedInUser loggedInUser = convertToLoggedInUser(jtCustomer);

				String jwt = jwtUtils.generateJwtToken(loggedInUser);
				String refreshToken = jwtUtils.generateRefreshToken(loggedInUser);

				List<String> roles = jtCustomer.getRoles().stream().map(role -> role.getName().name())
						.collect(Collectors.toList());

				return ResponseEntity.ok(new JwtResponse(jwt, loggedInUser.getId(), loggedInUser.getPrimaryContact(),
						loggedInUser.getEmail(), roles, refreshToken));
			}

		}
		return ResponseEntity.ok().body(new MessageResponse("Invalid PrimaryContact"));
	}

	private LoggedInUser convertToLoggedInUser(JTCustomer user) {
		LoggedInUser loggedInUser = new LoggedInUser();
		loggedInUser.setPrimaryContact(user.getUsername());
		loggedInUser.setId(user.getId());
		loggedInUser.setFullName(user.getFirstName());
		List<String> roles = user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toList());
		loggedInUser.setRoles(new HashSet<>(roles));
		return loggedInUser;
	}

	@GetMapping("/currentCustomer")
	public ResponseEntity<LoggedInUser> getCurrentCustomer(HttpServletRequest request)
			throws JsonProcessingException, ParseException {
		String jwt = parseJwt(request);
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			LoggedInUser loggedInUser = jwtUtils.getUserFromToken(jwt);
			JTCustomer user = jtCustomerService.findById(loggedInUser.getId());

			JTCustomerLastLogin customerLastLogin = new JTCustomerLastLoginImpl();
			JTCustomerLastLogin lastLogin = customerLastLoginDao.findByJtCustomer(user.getId());

			if (Objects.isNull(lastLogin) && null == lastLogin) {
				Date date = currentDate();
				customerLastLogin.setDate(date);
				customerLastLogin.setJtCustomer(user);
				customerLastLoginDao.save(customerLastLogin);
			} else if (Objects.nonNull(lastLogin) && null != lastLogin) {
				Date date = currentDate();
				lastLogin.setDate(date);
				lastLogin.setJtCustomer(user);
				customerLastLoginDao.save(lastLogin);
			}

			if (user != null) {
				loggedInUser.setNewUser(Boolean.TRUE);

				if (null != user.getFirstName()) {
					loggedInUser.setNewUser(Boolean.FALSE);
					loggedInUser.setFullName(user.getFirstName());
				}

				if (null != user.getEmail()) {
					loggedInUser.setEmail(user.getEmail());
				}

				if (null != user.getCustomerType()) {
					loggedInUser.setCustomerType(user.getCustomerType());
				}

				if (null != user.getUsername()) {
					loggedInUser.setPrimaryContact(user.getUsername());
				}

				return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
			}
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

}
