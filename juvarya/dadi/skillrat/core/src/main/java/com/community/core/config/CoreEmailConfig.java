package com.community.core.config;

import java.util.Properties;

import org.broadleafcommerce.common.email.service.info.EmailInfo;
import org.broadleafcommerce.common.email.service.message.MessageCreator;
import org.broadleafcommerce.common.email.service.message.NullMessageCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Shared email configuration
 * 
 * @author Phillip Verheyden (phillipuniverse)
 */
@Configuration
public class CoreEmailConfig {

	/**
	 * A dummy mail sender has been set to send emails for testing purposes only To
	 * view the emails sent use "DevNull SMTP" (download separately) with the
	 * following setting: Port: 30000
	 */
	@Bean
	public JavaMailSender blMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.sendgrid.net");
		mailSender.setPort(465);

		mailSender.setUsername("apikey");
		mailSender.setPassword("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");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.debug", "true");
		mailSender.setJavaMailProperties(props);
		return mailSender;
	}
	
	@Primary
	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtpout.secureserver.net");
		mailSender.setPort(465);

		mailSender.setUsername("noreply@skillrat.com");
		mailSender.setPassword("SkillRatEmail@2023");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.trust", "*");

		return mailSender;
	}

	/**
	 * Uncomment this bean to send real emails
	 */
//    @Bean
//    @Autowired
//    public MessageCreator blMessageCreator(@Qualifier("blEmailTemplateEngine") TemplateEngine tlTemplateEngine, @Qualifier("blMailSender") MailSender mailSender) {
//        return new ThymeleafMessageCreator(tlTemplateEngine, mailSender);
//    }

	@Bean
	@Autowired
	public MessageCreator blMessageCreator(@Qualifier("blMailSender") JavaMailSender mailSender) {
		return new NullMessageCreator(mailSender);
	}

	@Bean
	public EmailInfo blEmailInfo() {
		EmailInfo info = getEmailInfo();
		return info;
	}

	private EmailInfo getEmailInfo() {
		EmailInfo info = new EmailInfo();
		info.setFromAddress("info@fanfun.in");
		info.setSendAsyncPriority("2");
		info.setSendEmailReliableAsync("false");
		return info;
	}

	@Bean
	public EmailInfo blRegistrationEmailInfo() {
		EmailInfo info = getEmailInfo();
		info.setSubject("You have successfully registered!");
		info.setEmailType("REGISTER_CUSTOMER");
		info.setEmailTemplate("REGISTER_CUSTOMER");
		return info;
	}

	@Bean
	public EmailInfo blForgotPasswordEmailInfo() {
		EmailInfo info = getEmailInfo();
		info.setSubject("Reset password request");
		info.setEmailTemplate("resetPassword-email");
		return info;
	}

	@Bean
	public EmailInfo blOrderConfirmationEmailInfo() {
		EmailInfo info = getEmailInfo();
		info.setSubject("Your order with The Heat Clinic");
		info.setEmailTemplate("orderConfirmation-email");
		return info;
	}

	@Bean
	public EmailInfo blFulfillmentOrderTrackingEmailInfo() {
		EmailInfo info = getEmailInfo();
		info.setSubject("Your order with The Heat Clinic Has Shipped");
		info.setEmailTemplate("fulfillmentOrderTracking-email");
		return info;
	}

	@Bean
	public EmailInfo blReturnAuthorizationEmailInfo() {
		EmailInfo info = getEmailInfo();
		info.setSubject("Your return with The Heat Clinic");
		info.setEmailTemplate("returnAuthorization-email");
		return info;
	}

	@Bean
	public EmailInfo blReturnConfirmationEmailInfo() {
		EmailInfo info = getEmailInfo();
		info.setSubject("Your return with The Heat Clinic");
		info.setEmailTemplate("returnConfirmation-email");
		return info;
	}
}