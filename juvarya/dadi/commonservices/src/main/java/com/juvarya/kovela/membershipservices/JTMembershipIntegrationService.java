package com.juvarya.kovela.membershipservices;

import com.juvarya.kovela.commonservices.dto.JTProfileMembershipDTO;

public interface JTMembershipIntegrationService {
	JTProfileMembershipDTO createMembership(JTProfileMembershipDTO jtProfileMembership, String token);
	JTProfileMembershipDTO deleteMembership(Long profileId, String token);
	JTProfileMembershipDTO findByProfile(Long profileId, String token);
}
