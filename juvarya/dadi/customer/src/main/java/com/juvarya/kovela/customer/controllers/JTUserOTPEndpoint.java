package com.juvarya.kovela.customer.controllers;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juvarya.kovela.customer.dto.JTUserOTPDTO;
import com.juvarya.kovela.customer.model.JTUserOTPModel;
import com.juvarya.kovela.customer.populator.JTUserOTPPopulator;
import com.juvarya.kovela.customer.response.MessageResponse;
import com.juvarya.kovela.customer.service.JTCustomerService;
import com.juvarya.kovela.customer.service.JTUserOTPService;
import com.juvarya.kovela.customerservices.JTCustomerIntegrationService;
import com.juvarya.kovela.customerservices.impl.JTCustomerIntegrationServiceImpl;
import com.juvarya.kovela.utils.converter.AbstractConverter;
import com.juvarya.kovela.utils.converter.JTBaseEndpoint;

@SuppressWarnings("rawtypes")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/auth/jtuserotp")
public class JTUserOTPEndpoint extends JTBaseEndpoint {
	Logger logger = LoggerFactory.getLogger(JTUserOTPEndpoint.class);

	@Resource(name = "jtUserOTPService")
	private JTUserOTPService jtUserOTPService;

	@Resource(name = "jtUserOTPPopulator")
	private JTUserOTPPopulator jtUserOTPPopulator;

	@Resource(name = "jtCustomerService")
	private JTCustomerService customerService;

	@Value("${otp.trigger}")
	private boolean triggerOtp;

	@SuppressWarnings({ "unchecked", "unused" })
	@PostMapping("/trigger")
	public ResponseEntity saveJTUserOTP(@RequestBody JTUserOTPDTO jtUserOTPDTO)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
		logger.info("Entering Into Save JTUserOTP API");
		JTUserOTPModel jtUserOTP = new JTUserOTPModel();
		JTUserOTPDTO userOTPDTO = null;
		if (null != jtUserOTPDTO.getPrimaryContact() && !jtUserOTPDTO.getPrimaryContact().isEmpty()) {

			List<JTUserOTPModel> jtUserOTPModel = jtUserOTPService
					.findByPrimaryContactAndOtpType(jtUserOTPDTO.getPrimaryContact(), jtUserOTPDTO.getOtpType());
			JTCustomerIntegrationService jtCustomerIntegration = new JTCustomerIntegrationServiceImpl();

			if (!CollectionUtils.isEmpty(jtUserOTPModel)) {
				for (JTUserOTPModel otp : jtUserOTPModel) {
					jtUserOTPService.deleteOTP(otp);
				}
			}

			JTUserOTPModel userOtp = new JTUserOTPModel();

			populateUserOTPDTO(jtUserOTPDTO, userOtp);
			if (triggerOtp) {
				jtCustomerIntegration.triggerSMS(userOtp.getPrimaryContact(), userOtp.getOtp());
			}
			userOTPDTO = (JTUserOTPDTO) getConverterInstance().convert(userOtp);
		} else {
			return ResponseEntity.ok().body(new MessageResponse("Insufficient details"));
		}
		if (triggerOtp) {
			userOTPDTO.setOtp(null);
		}
		return new ResponseEntity<>(userOTPDTO, HttpStatus.OK);
	}

	private JTUserOTPModel populateUserOTPDTO(JTUserOTPDTO jtUserOTPDTO, JTUserOTPModel jtUserOTP) throws IOException {
		jtUserOTP.setChannel(jtUserOTPDTO.getChannel());
		jtUserOTP.setCreationTime(new Date());
		jtUserOTP.setPrimaryContact(jtUserOTPDTO.getPrimaryContact());
		jtUserOTP.setEmailAddress(jtUserOTPDTO.getEmailAddress());
		String otp = new DecimalFormat("000000").format(new Random().nextInt(999999));
		jtUserOTP.setOtpType(jtUserOTPDTO.getOtpType());
		jtUserOTP.setOtp(otp);
		return jtUserOTPService.save(jtUserOTP);
	}

	@SuppressWarnings("unchecked")
	private AbstractConverter getConverterInstance() {
		return getConverter(jtUserOTPPopulator, JTUserOTPDTO.class.getName());
	}

}
