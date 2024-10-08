package com.community.core.eventlistener;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@EnableAsync
@Component
public class JTEventListener implements ApplicationListener<JTEvent> {

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private Configuration configuration;

	@Override
	@Async
	public void onApplicationEvent(JTEvent event) {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper;

		if (event.isSave()) {
			try {
				Template t = configuration.getTemplate("OTPemailTemplate.html");
				String html2 = FreeMarkerTemplateUtils.processTemplateIntoString(t, event);
				helper = new MimeMessageHelper(message, true);
				helper.setFrom("noreply@skillrat.com");
				helper.setTo(event.getEmail());
				helper.setSubject("Confirm you email with OTP - SKILLRAT");
				helper.setText(html2, true);
				emailSender.send(message);

			} catch (MessagingException | IOException | TemplateException e) {
				e.printStackTrace();
			}
		}

		if (event.isUpdate()) {
			try {
				Template t = configuration.getTemplate("forgotPassword.html");
				String html2 = FreeMarkerTemplateUtils.processTemplateIntoString(t, event);
				helper = new MimeMessageHelper(message, true);
				helper.setFrom("techusertesting@gmail.com");
				helper.setTo(event.getEmail());
				helper.setSubject("FROM JUVARYA");
				helper.setText(html2, true);
				emailSender.send(message);

			} catch (MessagingException | IOException | TemplateException e) {
				e.printStackTrace();
			}
		}

		if (event.isInvite()) {
			try {
				Template t = configuration.getTemplate("invitation.html");

				String html2 = FreeMarkerTemplateUtils.processTemplateIntoString(t, event);
				helper = new MimeMessageHelper(message, true);
				helper.setFrom("techusertesting@gmail.com");
				helper.setTo(event.getEmail());
				helper.setSubject("FROM JUVARYA");
				helper.setText(html2, true);
				emailSender.send(message);

			} catch (MessagingException | IOException | TemplateException e) {
				e.printStackTrace();
			}
		}

		if (event.isEmailUpdate()) {
			try {
				Template t = configuration.getTemplate("updateEmail.html");

				String html2 = FreeMarkerTemplateUtils.processTemplateIntoString(t, event);
				helper = new MimeMessageHelper(message, true);
				helper.setFrom("techusertesting@gmail.com");
				helper.setTo(event.getEmail());
				helper.setSubject("FROM JUVARYA");
				helper.setText(html2, true);
				emailSender.send(message);

			} catch (MessagingException | IOException | TemplateException e) {
				e.printStackTrace();
			}
		}
	}
}
