package com.juvarya.kovela.customerservices;

import java.io.IOException;

import com.juvarya.kovela.commonservices.dto.JTMediaDTO;
import com.juvarya.kovela.commonservices.dto.JTPostalCodeDTO;
import com.juvarya.kovela.commonservices.dto.JTUserGroupDTO;
import com.juvarya.kovela.commonservices.dto.LoggedInUser;

public interface JTCustomerIntegrationService {
	LoggedInUser getCustomerDetails(Long customerId, String token);

	LoggedInUser getCustomerDetialsWithEmail(String email, String token);

	LoggedInUser currentCustomer(String token);

	LoggedInUser getUserDetails(Long customerId, String token);

	JTMediaDTO getCustomerProfilePicture(String primaryContact, String token);

	JTUserGroupDTO saveUserGroupDetails(JTUserGroupDTO userGroupDTO, String token);

	JTPostalCodeDTO getPostalCode(Long pincode, String token);
	
	boolean triggerSMS(String mobileNumber, String otp) throws IOException;
	
	LoggedInUser getCustomerDetialsWithPrimaryContact(String primaryContact, String token);
}
