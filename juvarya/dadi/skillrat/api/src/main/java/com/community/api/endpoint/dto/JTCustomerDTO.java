package com.community.api.endpoint.dto;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlTransient;

import org.broadleafcommerce.common.rest.api.wrapper.APIWrapper;
import org.broadleafcommerce.common.rest.api.wrapper.BaseWrapper;
import org.broadleafcommerce.profile.core.domain.CustomerPhone;
import org.springframework.context.ApplicationContext;

import com.broadleafcommerce.rest.api.wrapper.PhoneWrapper;
import com.community.core.catalog.domain.JTCustomer;
import com.fasterxml.jackson.annotation.JsonInclude;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTCustomerDTO extends BaseWrapper implements APIWrapper<JTCustomer> {

	@XmlTransient
	protected ApplicationContext context;

	private Long id;
	private String email;
	private String message;
	private String statusCode;
	private String primaryContact;
	private String password;
	private String otp;
	private String type;
	private String customerType;
	private String fullName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}

	@Override
	public void wrapDetails(JTCustomer model, HttpServletRequest request) {
		this.email = model.getEmail();
		this.fullName = model.getFullName();
		this.customerType = model.getCustomerType();

		if (!model.getCustomerPhones().isEmpty()) {
			List<CustomerPhone> phones = model.getCustomerPhones();
			for (CustomerPhone phone : phones) {
				PhoneWrapper phoneWrapper = (PhoneWrapper) context.getBean(PhoneWrapper.class.getName());
				phoneWrapper.wrapDetails(phone.getPhone(), request);
				this.primaryContact = phoneWrapper.getPhoneNumber();
			}
		}

	}

	@Override
	public void wrapSummary(JTCustomer model, HttpServletRequest request) {
		this.email = model.getEmail();
		this.fullName = model.getFullName();
		this.customerType = model.getCustomerType();

		if (!model.getCustomerPhones().isEmpty()) {
			List<CustomerPhone> phones = model.getCustomerPhones();
			for (CustomerPhone phone : phones) {
				PhoneWrapper phoneWrapper = (PhoneWrapper) context.getBean(PhoneWrapper.class.getName());
				phoneWrapper.wrapDetails(phone.getPhone(), request);
				this.primaryContact = phoneWrapper.getPhoneNumber();
			}
		}
	}
}
