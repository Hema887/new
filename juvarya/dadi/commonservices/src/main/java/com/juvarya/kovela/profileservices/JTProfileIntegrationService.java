package com.juvarya.kovela.profileservices;

import com.juvarya.kovela.commonservices.dto.JTAddressDTO;
import com.juvarya.kovela.commonservices.dto.JTCustomerProfileDTO;
import com.juvarya.kovela.commonservices.dto.JTDCommunityDTO;
import com.juvarya.kovela.commonservices.dto.JTDCommunityListDTO;
import com.juvarya.kovela.commonservices.dto.JTDonationTypeDTO;
import com.juvarya.kovela.commonservices.dto.JTMediaDTO;
import com.juvarya.kovela.commonservices.dto.JTProfileDTO;
import com.juvarya.kovela.commonservices.dto.JTProfileHistoryDTO;
import com.juvarya.kovela.commonservices.dto.JTProfileHistoryToMediaDTO;

public interface JTProfileIntegrationService {
	JTProfileDTO getProfileDetails(Long profileId, String token);

	JTMediaDTO getProfilePicture(Long profileId, String token);

	JTCustomerProfileDTO getCustomerRole(Long profileId, String token);

	JTCustomerProfileDTO findByProfile(Long profileId, String token);

	JTDonationTypeDTO findByDonationType(String type, String token);

	JTAddressDTO getProfileAddress(Long profileId, String token);

	Long getFollowersCount(Long profileId, String token);

	JTDCommunityDTO getCommunity(Long communityId, String token);

	JTDCommunityListDTO getCommunities(String token);

	JTProfileHistoryDTO getProfileHistory(Long historyId, String token);

	JTProfileHistoryToMediaDTO getHistoryMedia(Long historyId, String token);

}
