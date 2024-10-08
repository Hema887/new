package com.juvarya.kovela.feedservices.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.juvarya.kovela.commonservices.dto.JTFeedDTO;
import com.juvarya.kovela.commonservices.dto.JTFollowerDTO;
import com.juvarya.kovela.commonservices.dto.JTMediaDTO;
import com.juvarya.kovela.commonservices.dto.JTNotificationDTO;
import com.juvarya.kovela.commonservices.dto.JTNotificationListDTO;
import com.juvarya.kovela.feedservices.JTFeedIntegrationService;
import com.juvarya.kovela.utils.JTConstants;

public class JTFeedIntegrationServiceImpl implements JTFeedIntegrationService {

	private static final String HTTP_LOCALHOST_9094 = "http://localhost:9094/";
	private static final String BEARER = "Bearer ";
	private static final String AUTHORIZATION = "Authorization";

	@Override
	public JTFeedDTO getFeedDetails(Long feedId, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set(AUTHORIZATION, BEARER + token);
			ResponseEntity<JTFeedDTO> response = new RestTemplate().exchange(HTTP_LOCALHOST_9094 + "jtfeed/" + feedId,
					HttpMethod.GET, entity, JTFeedDTO.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public Boolean isFollowing(Long profileId, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set(AUTHORIZATION, BEARER + token);
			ResponseEntity<Boolean> response = new RestTemplate().exchange(
					HTTP_LOCALHOST_9094 + "jtfollower/profile/" + profileId, HttpMethod.GET, entity, Boolean.class);
			return response.getBody();
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public JTFeedDTO removeProfileFeed(Long profileId, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set(AUTHORIZATION, BEARER + token);
			ResponseEntity<JTFeedDTO> response = new RestTemplate().exchange(
					HTTP_LOCALHOST_9094 + "jtfeed/profile?profileId=" + profileId, HttpMethod.DELETE, entity,
					JTFeedDTO.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}

	}

	public Boolean isLike(Long feedId, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set(AUTHORIZATION, BEARER + token);
			ResponseEntity<Boolean> response = new RestTemplate().exchange(
					HTTP_LOCALHOST_9094 + "jtfeedreview/feed/" + feedId, HttpMethod.GET, entity, Boolean.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public Boolean isSavedPost(Long feedId, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set(AUTHORIZATION, BEARER + token);
			ResponseEntity<Boolean> response = new RestTemplate().exchange(
					HTTP_LOCALHOST_9094 + "jtfeedtocustomer/feed/" + feedId, HttpMethod.GET, entity, Boolean.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public JTNotificationDTO getNotificationDetails(Long notificationId, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set(AUTHORIZATION, BEARER + token);
			ResponseEntity<JTNotificationDTO> response = new RestTemplate().exchange(
					HTTP_LOCALHOST_9094 + "/jtNotification/" + notificationId, HttpMethod.GET, entity,
					JTNotificationDTO.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public JTNotificationListDTO getByProfileNotifications(Long profileId, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set(AUTHORIZATION, BEARER + token);
			ResponseEntity<JTNotificationListDTO> response = new RestTemplate().exchange(
					HTTP_LOCALHOST_9094 + "jtNotification/jtProfile?profileId=" + profileId, HttpMethod.GET, entity,
					JTNotificationListDTO.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public JTNotificationDTO saveJtNotification(JTNotificationDTO jtNotificationDTO, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<JTNotificationDTO> entity = new HttpEntity<>(jtNotificationDTO, headers);
			headers.set(JTConstants.AUTHORIZATION, JTConstants.BEARER + token);
			ResponseEntity<JTNotificationDTO> response = new RestTemplate().exchange(
					HTTP_LOCALHOST_9094 + "jtNotification/save", HttpMethod.POST, entity, JTNotificationDTO.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public JTNotificationListDTO getNotificationsByType(String type, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set(AUTHORIZATION, BEARER + token);
			ResponseEntity<JTNotificationListDTO> response = new RestTemplate().exchange(
					HTTP_LOCALHOST_9094 + "jtNotification/type?type=" + type, HttpMethod.GET, entity,
					JTNotificationListDTO.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public JTFollowerDTO saveFollower(JTFollowerDTO followerDTO, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<JTFollowerDTO> entity = new HttpEntity<>(followerDTO, headers);
			headers.set(JTConstants.AUTHORIZATION, JTConstants.BEARER + token);
			ResponseEntity<JTFollowerDTO> response = new RestTemplate().exchange(
					HTTP_LOCALHOST_9094 + "jtfollower/saveInvitedFollower", HttpMethod.POST, entity,
					JTFollowerDTO.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public JTMediaDTO getCustomerPicture(String primaryContact, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(primaryContact, headers);
			headers.set(JTConstants.AUTHORIZATION, JTConstants.BEARER + token);
			ResponseEntity<JTMediaDTO> response = new RestTemplate().exchange(
					HTTP_LOCALHOST_9094 + "picture/customer?primaryContact=" + primaryContact, HttpMethod.GET, entity,
					JTMediaDTO.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public JTFeedDTO getProfileFeeds(Long profileId, String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(null, headers);
			headers.set(JTConstants.AUTHORIZATION, JTConstants.BEARER + token);
			ResponseEntity<JTFeedDTO> response = new RestTemplate().exchange(
					HTTP_LOCALHOST_9094 + "jtfeed/feedsOfProfile?id= " + profileId, HttpMethod.GET, entity,
					JTFeedDTO.class);
			return response.getBody();
		} catch (Exception e) {
			return null;
		}
	}
}
