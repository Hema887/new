package com.juvarya.kovela.customer.populator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.juvarya.kovela.customer.dto.JTUserOTPDTO;
import com.juvarya.kovela.customer.model.JTUserOTPModel;
import com.juvarya.kovela.utils.converter.Populator;

@Component("jtUserOTPPopulator")
public class JTUserOTPPopulator implements Populator<JTUserOTPModel, JTUserOTPDTO> {

	@Value("${otp.trigger}")
	private boolean triggerOtp;

	@Override
	public void populate(JTUserOTPModel source, JTUserOTPDTO target) {
		target.setId(source.getId());
		target.setCreationTime(source.getCreationTime());
		target.setEmailAddress(source.getEmailAddress());
		target.setChannel(source.getChannel());
		target.setOtpType(source.getOtpType());
		if (!triggerOtp) {
			target.setOtp(source.getOtp());
		}
		target.setUser(source.getUser());

	}
}
