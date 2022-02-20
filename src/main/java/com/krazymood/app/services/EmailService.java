package com.krazymood.app.services;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.krazymood.app.entities.Contents;
import com.krazymood.app.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.util.List;


@Service("emailService")
public class EmailService {

	 @Autowired
  private JavaMailSender mailSender;
	@Autowired
	FirebaseService firebaseService;

  
  @Async
  public void sendEmail(SimpleMailMessage email) {
	  System.out.println("Sending email...");
	  mailSender.send(email);
	  System.out.println(".................done");
  }


	@Async
	public void sendUpdatesToUsers(List<Users> listOfUsers, Contents contents) {
		for (Users user : listOfUsers) {
			mailSender.send(new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws MessagingException {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
					message.setTo(user.getEmail());
					message.setSubject(contents.getHeader());
					
					ByteArrayDataSource imgDS = new ByteArrayDataSource( firebaseService.fetchFile("public_images/"+contents.getImgname()), "image/png");
					message.addInline("cImage", imgDS);
					
					message.setText("<h1>Hi " + user.getName() + ",</h1><br><table>"
									+ "<p>Please find the latest update by us, We hope that you'll find this update userful and share with your friends.</p>"
									+ "<div><p>" + contents.getText() + "</p><p><img src='cImage' /></p></div>"
							, true);
					

				}
			});
			System.out.println("............................done.");
		}

	}


}
