package com.juvarya.kovela.profileservices.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.juvarya.kovela.commonservices.dto.JTAddressDTO;
import com.juvarya.kovela.commonservices.dto.JTCustomerProfileDTO;
import com.juvarya.kovela.commonservices.dto.JTDCommunityDTO;
import com.juvarya.kovela.commonservices.dto.JTDCommunityListDTO;
import com.juvarya.kovela.commonservices.dto.JTDonationTypeDTO;
import com.juvarya.kovela.commonservices.dto.JTMediaDTO;
import com.juvarya.kovela.commonservices.dto.JTNotificationListDTO;
import com.juvarya.kovela.commonservices.dto.JTProfileDTO;
import com.juvarya.kovela.commonservices.dto.JTProfileHistoryDTO;
import com.juvarya.kovela.commonservices.dto.JTProfileHistoryToMediaDTO;
import com.juvarya.kovela.profileservices.JTProfileIntegrationService;

public class JTProfileIntegrationServiceImpl implements JTProfileIntegrationService {
	Logger logger = LoggerFactory.getLogger(JTProfileIntegrationServiceImpl.class);

	@Override
	public JTProfileDTO getProfileDetails(Long profileId, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set("Authorization", "Bearer " + token);
			ResponseEntity<JTProfileDTO> response = new RestTemplate().exchange(
					"http://localhost:9096/jtprofile/" + profileId, HttpMethod.GET, entity, JTProfileDTO.class);
			logger.info("Popular Temple Feeds List");
			return response.getBody();
		} catch (Exception e) {
			logger.error("Popular Temple Feeds Found With Zero Result");
			return null;
		}

	}

	@Override
	public JTMediaDTO getProfilePicture(Long profileId, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set("Authorization", "Bearer " + token);
			ResponseEntity<JTMediaDTO> response = new RestTemplate().exchange(
					"http://localhost:9094/picture/profile?profileId=" + profileId, HttpMethod.GET, entity,
					JTMediaDTO.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public JTCustomerProfileDTO getCustomerRole(Long profileId, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set("Authorization", "Bearer " + token);
			ResponseEntity<JTCustomerProfileDTO> response = new RestTemplate().exchange(
					"http://localhost:9096/jtprofile/customer-roles?profileId=" + profileId, HttpMethod.GET, entity,
					JTCustomerProfileDTO.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public JTCustomerProfileDTO findByProfile(Long profileId, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set("Authorization", "Bearer " + token);
			ResponseEntity<JTCustomerProfileDTO> response = new RestTemplate().exchange(
					"http://localhost:9096/jtprofile/list/roles?profileId=" + profileId, HttpMethod.GET, entity,
					JTCustomerProfileDTO.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public JTDonationTypeDTO findByDonationType(String type, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set("Authorization", "Bearer " + token);
			ResponseEntity<JTDonationTypeDTO> response = new RestTemplate().exchange(
					"http://localhost:9096/donationtype/type?type=" + type, HttpMethod.GET, entity,
					JTDonationTypeDTO.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public JTAddressDTO getProfileAddress(Long profileId, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set("Authorization", "Bearer " + token);
			ResponseEntity<JTAddressDTO> response = new RestTemplate().exchange(
					"http://localhost:9092/jtAddress/default/" + profileId, HttpMethod.GET, entity, JTAddressDTO.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Long getFollowersCount(Long profileId, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set("Authorization", "Bearer " + token);
			ResponseEntity<Long> response = new RestTemplate().exchange(
					"http://localhost:9094/jtfollower/count/" + profileId, HttpMethod.GET, entity, Long.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public JTDCommunityDTO getCommunity(Long communityId, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set("Authorization", "Bearer " + token);
			ResponseEntity<JTDCommunityDTO> response = new RestTemplate().exchange(
					"http://localhost:9096/jtdcommunities/" + communityId, HttpMethod.GET, entity,
					JTDCommunityDTO.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public JTDCommunityListDTO getCommunities(String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set("Authorization", "Bearer " + token);
			ResponseEntity<JTDCommunityListDTO> response = new RestTemplate().exchange(
					"http://localhost:9096/jtdcommunities/list", HttpMethod.GET, entity, JTDCommunityListDTO.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public JTProfileHistoryDTO getProfileHistory(Long historyId, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set("Authorization", "Bearer " + token);
			ResponseEntity<JTProfileHistoryDTO> response = new RestTemplate().exchange(
					"http://localhost:9096/jtProfileHistory/find/" + historyId, HttpMethod.GET, entity,
					JTProfileHistoryDTO.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public JTProfileHistoryToMediaDTO getHistoryMedia(Long historyId, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set("Authorization", "Bearer " + token);
			ResponseEntity<JTProfileHistoryToMediaDTO> response = new RestTemplate().exchange(
					"http://localhost:9094/jtProfileHistoryToMedia/media/" + historyId, HttpMethod.GET, entity,
					JTProfileHistoryToMediaDTO.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}
	}

}
