package com.community.api.endpoint.cart;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.broadleafcommerce.rest.api.endpoint.BaseEndpoint;
import com.community.api.endpoint.dto.JTCustomerDTO;
import com.community.api.endpoint.dto.LoggedInUser;
import com.community.api.jwt.payload.response.MessageResponse;
import com.community.api.jwt.services.CustomerState;
import com.community.core.catalog.domain.JTCustomer;
import com.community.core.catalog.service.JTCustomerService;

@RestController
@RequestMapping(value = "/jtcustomer", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class JTCustomerEndpoint extends BaseEndpoint {

	@Resource(name = "jtCustomerService")
	private JTCustomerService jtCustomerService;

	@Resource(name = "customerState")
	private CustomerState customerState;

	@GetMapping("/{customerId}")
	public LoggedInUser findCustomerById(@PathVariable("customerId") Long customerId, HttpServletRequest request) {
		JTCustomer customer = (JTCustomer) jtCustomerService.findById(customerId);
		LoggedInUser loggedInUser = new LoggedInUser();
		loggedInUser.setId(customer.getId());
		loggedInUser.setFullName(customer.getFirstName());
		List<String> roles = customer.getRoles().stream().map(role -> role.getName().name())
				.collect(Collectors.toList());
		loggedInUser.setRoles(new HashSet<>(roles));
		return loggedInUser;
	}

	@SuppressWarnings({ "rawtypes" })
	@PutMapping("/userDetails")
	public ResponseEntity updateUserDetails(@RequestBody JTCustomerDTO customerDTO, HttpServletRequest request) {
		LoggedInUser loggedInUser = customerState.getLoggedInUser(request);

		JTCustomer customer = jtCustomerService.findByPrimaryContact(customerDTO.getPrimaryContact());
		if (null != customer && null != customer.getEmailAddress()
				&& loggedInUser.getEmail().equals(customer.getEmailAddress())) {

			if (null != customerDTO.getFullName()) {
				customer.setFirstName(customerDTO.getFullName());
			}

			JTCustomer jtCustomer = jtCustomerService.findByEmail(customerDTO.getEmail());

			if (null != customerDTO.getEmail() && null == jtCustomer) {
				customer.setEmail(customerDTO.getEmail());
			} else if (null != jtCustomer) {
				return ResponseEntity.ok().body(new MessageResponse("Error: Email Is Already In Use"));
			}

			if (null != customerDTO.getCustomerType()) {
				customer.setCustomerType(customerDTO.getCustomerType());
			}
			jtCustomerService.save(customer);
			return ResponseEntity.ok().body(new MessageResponse("User Details Updated"));
		}
		return ResponseEntity.ok().body(new MessageResponse("User Not Found"));
	}

}
