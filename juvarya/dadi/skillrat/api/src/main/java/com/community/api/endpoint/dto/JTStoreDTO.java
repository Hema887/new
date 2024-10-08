package com.community.api.endpoint.dto;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.broadleafcommerce.common.rest.api.wrapper.APIWrapper;
import org.broadleafcommerce.common.rest.api.wrapper.BaseWrapper;
import org.springframework.context.ApplicationContext;

import com.community.core.catalog.domain.JTStore;
import com.fasterxml.jackson.annotation.JsonInclude;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JTStoreDTO extends BaseWrapper implements APIWrapper<JTStore> {

	@XmlTransient
	protected ApplicationContext context;

	@XmlElement
	private Long id;

	@XmlElement
	private String name;

	@XmlElement
	private String storeNumber;

	@XmlElement
	private boolean open;

	@XmlElement
	private String storeHours;

	@XmlElement
	private Date creationTime;

	@XmlElement
	private boolean active;

	@XmlElement
	private Double latitude;

	@XmlElement
	private Double longitude;

	@XmlElement
	private JTAddressDTO addressDTO;

	@XmlElement
	private Long customerId;

	@XmlElement
	private JTCustomerDTO customerDTO;

	@XmlElement
	private String contact;

	@XmlElement
	private JTMediaDTO profilePicture;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getStoreHours() {
		return storeHours;
	}

	public void setStoreHours(String storeHours) {
		this.storeHours = storeHours;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public JTAddressDTO getAddressDTO() {
		return addressDTO;
	}

	public void setAddressDTO(JTAddressDTO addressDTO) {
		this.addressDTO = addressDTO;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public JTCustomerDTO getCustomerDTO() {
		return customerDTO;
	}

	public void setCustomerDTO(JTCustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public JTMediaDTO getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(JTMediaDTO profilePicture) {
		this.profilePicture = profilePicture;
	}

	@Override
	public void wrapDetails(JTStore model, HttpServletRequest request) {
		this.id = model.getId();
		this.name = model.getName();
		this.open = model.getOpen();
		this.storeHours = model.getStoreHours();
		this.creationTime = model.getCreationTime();
		this.active = model.getActive();
		this.latitude = model.getLatitude();
		this.longitude = model.getLongitude();
		
//		if(null != model.getAddress()) {
//			AddressWrapper addressWrapper = (AddressWrapper) context.getBean(AddressWrapper.class.getName());
//			addressWrapper.wrapDetails(model.getAddress(), request);
//			this.addressDTO = addressWrapper;
//		}
		

	}

	@Override
	public void wrapSummary(JTStore model, HttpServletRequest request) {
		// TODO Auto-generated method stub

	}

}
