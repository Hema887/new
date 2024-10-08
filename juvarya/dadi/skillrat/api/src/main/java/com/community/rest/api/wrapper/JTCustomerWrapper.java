package com.community.rest.api.wrapper;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.broadleafcommerce.common.persistence.Status;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerAttribute;
import org.broadleafcommerce.profile.core.service.CustomerService;
import org.springframework.context.ApplicationContext;

import com.broadleafcommerce.rest.api.wrapper.CustomerAttributeWrapper;
import com.broadleafcommerce.rest.api.wrapper.CustomerWrapper;
import com.community.core.catalog.domain.impl.JTCustomerImpl;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@XmlRootElement(name = "JTCustomer")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class JTCustomerWrapper extends CustomerWrapper {
	private static final long serialVersionUID = 1L;

	@JsonProperty(access = Access.WRITE_ONLY)
	protected String password;

	public JTCustomerImpl convertToModel(JTCustomerWrapper customerWrapper, HttpServletRequest request) {
		JTCustomerImpl model = new JTCustomerImpl();
		model.setId(customerWrapper.getId());
		model.setFirstName(customerWrapper.getFirstName());
		model.setLastName(customerWrapper.getLastName());
		model.setEmailAddress(customerWrapper.getEmailAddress());
		model.setPassword(customerWrapper.getPassword());
		return model;
	}

	public void wrapDetails(Customer model, JTCustomerWrapper customerWrapper, HttpServletRequest request) {
		this.id = model.getId();
		this.firstName = model.getFirstName();
		this.lastName = model.getLastName();
		this.emailAddress = model.getEmailAddress();
		this.registered = model.isRegistered();
		this.password = model.getPassword();
		if (model.getCustomerAttributes() != null && !model.getCustomerAttributes().isEmpty()) {
			Map<String, CustomerAttribute> itemAttributes = model.getCustomerAttributes();
			this.customerAttributes = new ArrayList<CustomerAttributeWrapper>();
			Set<String> keys = itemAttributes.keySet();
			for (String key : keys) {
				CustomerAttributeWrapper customerAttributeWrapper = (CustomerAttributeWrapper) context
						.getBean(CustomerAttributeWrapper.class.getName());
				customerAttributeWrapper.wrapDetails(itemAttributes.get(key), request);
				this.customerAttributes.add(customerAttributeWrapper);
			}
		}

		if (model instanceof Status) {
			this.archived = ((Status) model).getArchived();
		}
	}

	public void wrapDetails(Customer model,  HttpServletRequest request) {
		this.id = model.getId();
		this.firstName = model.getFirstName();
		this.lastName = model.getLastName();
		this.emailAddress = model.getEmailAddress();
		this.registered = model.isRegistered();
		this.password = model.getPassword();
	}

	@Override
	public void wrapSummary(Customer model, HttpServletRequest request) {
		wrapDetails(model, request);
	}

	@Override
	public Customer unwrap(HttpServletRequest request, ApplicationContext context) {
		CustomerService customerService = (CustomerService) context.getBean("blCustomerService");
		Customer customer = customerService.createCustomerFromId(this.id);
		customer.setFirstName(this.firstName);
		customer.setLastName(this.lastName);
		customer.setEmailAddress(this.emailAddress);
		customer.setUnencodedPassword(this.password);
		if (customerAttributes != null) {
			for (CustomerAttributeWrapper customerAttributeWrapper : customerAttributes) {
				CustomerAttribute attribute = customerAttributeWrapper.unwrap(request, context);
				attribute.setCustomer(customer);
				customer.getCustomerAttributes().put(attribute.getName(), attribute);
			}
		}
		return customer;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
