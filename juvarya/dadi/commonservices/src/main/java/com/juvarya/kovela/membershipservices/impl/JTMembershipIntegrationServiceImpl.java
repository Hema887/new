package com.juvarya.kovela.membershipservices.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.juvarya.kovela.commonservices.dto.JTProfileMembershipDTO;
import com.juvarya.kovela.membershipservices.JTMembershipIntegrationService;
import com.juvarya.kovela.utils.JTConstants;

public class JTMembershipIntegrationServiceImpl implements JTMembershipIntegrationService {
	@Override
	public JTProfileMembershipDTO createMembership(JTProfileMembershipDTO jtProfileMembership, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<JTProfileMembershipDTO> entity = new HttpEntity<>(jtProfileMembership, headers);
			headers.set(JTConstants.AUTHORIZATION, JTConstants.BEARER + token);
			ResponseEntity<JTProfileMembershipDTO> response = new RestTemplate().exchange(
					"http://localhost:9095/jtProfileMembership/create", HttpMethod.POST, entity,
					JTProfileMembershipDTO.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public JTProfileMembershipDTO deleteMembership(Long profileId, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set(JTConstants.AUTHORIZATION, JTConstants.BEARER + token);
			ResponseEntity<JTProfileMembershipDTO> response = new RestTemplate().exchange(
					"http://localhost:9095/jtProfileMembership/delete?profileId=" + profileId, HttpMethod.DELETE,
					entity, JTProfileMembershipDTO.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public JTProfileMembershipDTO findByProfile(Long profileId, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set(JTConstants.AUTHORIZATION, JTConstants.BEARER + token);
			ResponseEntity<JTProfileMembershipDTO> response = new RestTemplate().exchange(
					"http://localhost:9095/jtProfileMembership/profile?profileId=" + profileId, HttpMethod.GET, entity,
					JTProfileMembershipDTO.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}

	}
}
