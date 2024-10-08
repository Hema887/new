package com.juvarya.kovela.customerservices.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.juvarya.kovela.commonservices.dto.JTMediaDTO;
import com.juvarya.kovela.commonservices.dto.JTPostalCodeDTO;
import com.juvarya.kovela.commonservices.dto.JTUserGroupDTO;
import com.juvarya.kovela.commonservices.dto.LoggedInUser;
import com.juvarya.kovela.customerservices.JTCustomerIntegrationService;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JTCustomerIntegrationServiceImpl implements JTCustomerIntegrationService {

	@Value("${otp.trigger}")
	private boolean triggerOtp;

	@Override
	public LoggedInUser getCustomerDetails(Long customerId, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set("Authorization", "Bearer " + token);
			ResponseEntity<LoggedInUser> response = new RestTemplate().exchange(
					"http://localhost:9092/api/customer/find/" + customerId, HttpMethod.GET, entity,
					LoggedInUser.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public LoggedInUser getCustomerDetialsWithEmail(String email, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set("Authorization", "Bearer " + token);
			ResponseEntity<LoggedInUser> response = new RestTemplate().exchange(
					"http://localhost:9092/api/customer/byemail/" + email, HttpMethod.GET, entity, LoggedInUser.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public LoggedInUser currentCustomer(String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set("Authorization", "Bearer " + token);
			ResponseEntity<LoggedInUser> response = new RestTemplate().exchange(
					"http://localhost:9092/api/auth/currentCustomer", HttpMethod.GET, entity, LoggedInUser.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}

	}

	public LoggedInUser getUserDetails(Long customerId, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set("Authorization", "Bearer " + token);
			ResponseEntity<LoggedInUser> response = new RestTemplate().exchange(
					"http://localhost:9092/api/customer/find/" + customerId, HttpMethod.GET, entity, LoggedInUser.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public JTMediaDTO getCustomerProfilePicture(String primaryContact, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set("Authorization", "Bearer " + token);
			ResponseEntity<JTMediaDTO> response = new RestTemplate().exchange(
					"http://localhost:9094/picture/customer?primaryContact=" + primaryContact, HttpMethod.GET, entity, JTMediaDTO.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public JTUserGroupDTO saveUserGroupDetails(JTUserGroupDTO userGroupDTO, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<JTUserGroupDTO> entity = new HttpEntity<>(userGroupDTO, headers);
			headers.set("Authorization", "Bearer " + token);
			ResponseEntity<JTUserGroupDTO> response = new RestTemplate()
					.exchange("http://localhost:9092/jtUserGroup/save", HttpMethod.POST, entity, JTUserGroupDTO.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public JTPostalCodeDTO getPostalCode(Long pincode, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set("Authorization", "Bearer " + token);
			ResponseEntity<JTPostalCodeDTO> response = new RestTemplate().exchange(
					"http://localhost:9092/jtpostalcode/postalCode?code=" + pincode, HttpMethod.GET, entity,
					JTPostalCodeDTO.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean triggerSMS(String mobileNumber, String otp) throws IOException {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url("https://api.authkey.io/request?authkey=5784b393579d5d3d&mobile="
				+ mobileNumber + "&country_code=+91&sid=12135&name=Twinkle&otp=" + otp).get().build();
		Response response = client.newCall(request).execute();
		return response.isSuccessful();
	}

	@Override
	public LoggedInUser getCustomerDetialsWithPrimaryContact(String primaryContact, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set("Authorization", "Bearer " + token);
			ResponseEntity<LoggedInUser> response = new RestTemplate().exchange(
					"http://localhost:9092/api/customer/contact?primaryContact=" + primaryContact, HttpMethod.GET, entity,
					LoggedInUser.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}
	}

}
