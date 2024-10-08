package com.juvarya.kovela.feedservices;

import com.juvarya.kovela.commonservices.dto.JTFeedDTO;
import com.juvarya.kovela.commonservices.dto.JTFollowerDTO;
import com.juvarya.kovela.commonservices.dto.JTMediaDTO;
import com.juvarya.kovela.commonservices.dto.JTNotificationDTO;
import com.juvarya.kovela.commonservices.dto.JTNotificationListDTO;

public interface JTFeedIntegrationService {
	
	JTFeedDTO getFeedDetails(Long feedId, String token);

	Boolean isFollowing(Long ProfileId, String token);

	JTFeedDTO removeProfileFeed(Long profileId, String token);

	Boolean isLike(Long feedId, String token);

	Boolean isSavedPost(Long feedId, String token);

	JTNotificationDTO getNotificationDetails(Long notificationId, String token);

	JTNotificationListDTO getByProfileNotifications(Long profileId, String token);
	
	JTNotificationDTO saveJtNotification(JTNotificationDTO jtNotificationDTO, String token);
	
	JTNotificationListDTO getNotificationsByType(String type,String token);

	JTFollowerDTO saveFollower(JTFollowerDTO followerDTO, String token);
	
	JTMediaDTO getCustomerPicture(String primaryContact, String token);
	
	JTFeedDTO getProfileFeeds(Long profileId,String token);
}
