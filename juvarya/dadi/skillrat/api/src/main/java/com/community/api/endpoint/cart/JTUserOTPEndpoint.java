package com.community.api.endpoint.cart;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import javax.annotation.Resource;

import org.broadleafcommerce.profile.core.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.broadleafcommerce.rest.api.endpoint.BaseEndpoint;
import com.community.api.endpoint.dto.JTUserOTPDTO;
import com.community.core.catalog.domain.JTUserOTP;
import com.community.core.catalog.domain.impl.JTUserOTPImpl;
import com.community.core.catalog.service.JTUserOTPService;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@SuppressWarnings("rawtypes")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/auth/jtuserotp")
public class JTUserOTPEndpoint extends BaseEndpoint {
	private static final long OTP_VALID_DURATION = 15 * 60 * 1000;

	@Autowired
	private ModelMapper modelMapper;

	@Resource(name = "jtUserOTPService")
	private JTUserOTPService jtUserOTPService;

	@Resource(name = "blCustomerService")
	private CustomerService customerService;

	@Value("${otp.trigger}")
	private boolean triggerOtp;

	@PostMapping("/trigger")
	public JTUserOTPDTO saveJTUserOTP(@RequestBody JTUserOTPDTO jtUserOTPDTO) throws IOException {
		JTUserOTPDTO response = new JTUserOTPDTO();
		JTUserOTP jtUserOTP = new JTUserOTPImpl();

		if (null != jtUserOTPDTO.getPrimaryContact() && !jtUserOTPDTO.getPrimaryContact().isEmpty()) {

			JTUserOTP userOTP = jtUserOTPService.findByPrimaryContactAndOtpType(jtUserOTPDTO.getPrimaryContact(),
					jtUserOTPDTO.getOtpType());

			if (!Objects.nonNull(userOTP)) {
				populateUserOTPDTO(jtUserOTPDTO, jtUserOTP);
				if (triggerOtp) {
					triggerSMS(jtUserOTPDTO.getPrimaryContact(), jtUserOTP.getOtp());
				}
				jtUserOTPDTO = modelMapper.map(jtUserOTP, JTUserOTPDTO.class);
			} else if (!validateOtpTime(userOTP)) {
				if (triggerOtp) {
					triggerSMS(jtUserOTPDTO.getPrimaryContact(), userOTP.getOtp());
				}
				jtUserOTPDTO = modelMapper.map(userOTP, JTUserOTPDTO.class);
			} else {
				jtUserOTPService.delete(userOTP.getId());
				populateUserOTPDTO(jtUserOTPDTO, jtUserOTP);
				if (triggerOtp) {
					triggerSMS(jtUserOTPDTO.getPrimaryContact(), jtUserOTP.getOtp());
				}
				jtUserOTPDTO = modelMapper.map(jtUserOTP, JTUserOTPDTO.class);
			}

		} else {
			response.setStatusCode("403");
			response.setMessage("Insufficient details");
			return response;
		}
		if (triggerOtp) {
			jtUserOTPDTO.setOtp(null);
		}
		return jtUserOTPDTO;
	}

	private JTUserOTP populateUserOTPDTO(JTUserOTPDTO jtUserOTPDTO, JTUserOTP jtUserOTP) throws IOException {
		jtUserOTP.setChannel(jtUserOTPDTO.getChannel());
		jtUserOTP.setCreationTime(new Date());
		jtUserOTP.setPrimaryContact(jtUserOTPDTO.getPrimaryContact());
		jtUserOTP.setEmailAddress(jtUserOTPDTO.getEmailAddress());
		String otp = new DecimalFormat("000000").format(new Random().nextInt(999999));
		jtUserOTP.setOtpType(jtUserOTPDTO.getOtpType());
		jtUserOTP.setOtp(otp);
		return jtUserOTPService.save(jtUserOTP);
	}

	public boolean triggerSMS(String mobileNumber, String otp) throws IOException {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url("https://api.authkey.io/request?authkey=5784b393579d5d3d&mobile="
				+ mobileNumber + "&country_code=+91&sid=12135&name=Twinkle&otp=" + otp).get().build();
		Response response = client.newCall(request).execute();
		return response.isSuccessful();
	}

	private boolean validateOtpTime(JTUserOTP userOtpModel) {
		long currentTimeInMillis = System.currentTimeMillis();
		long otpCreationTimeInMillis = userOtpModel.getCreationTime().getTime();
		if (otpCreationTimeInMillis + OTP_VALID_DURATION > currentTimeInMillis) {
			return false;
		}
		return true;
	}
}
