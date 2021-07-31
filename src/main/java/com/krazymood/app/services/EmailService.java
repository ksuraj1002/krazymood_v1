package com.krazymood.app.services;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.krazymood.app.model.Mail;

@Service
public class EmailService {

	  @Autowired
	    private JavaMailSender emailSender;

	    @Autowired
	    private SpringTemplateEngine templateEngine;
	
	public void sendSimpleMessage(Mail mail) throws MessagingException {
			MimeMessage message = emailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(message,
		                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
		                StandardCharsets.UTF_8.name());
			 
	        Context context = new Context();
	        context.setVariables(mail.getModel());
	      
	        String html = templateEngine.process("email-template", context);

	        helper.setFrom(mail.getFrom());
	        helper.setTo(mail.getTo());
	        helper.setSubject(mail.getSubject());
	        helper.setText(html, true);

	        emailSender.send(message);
		
	}

}
