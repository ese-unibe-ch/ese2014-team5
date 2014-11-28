package org.sample.controller.service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.mail.internet.MimeMessage;
import org.sample.model.Notifies;
import org.sample.model.User;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 *This class sends the E-mails to the accounts to announce an notification
 * 
 */
public class EmailSender {
    
    
public void GenerateAnEmail(Notifies notifies) throws MessagingException {

JavaMailSenderImpl sender = new JavaMailSenderImpl(); 
sender.setHost(notifies.getFromUser().getEmail());

MimeMessage message = sender.createMimeMessage();
MimeMessageHelper helper = new MimeMessageHelper(message);
String username = notifies.getToUser().getUsername();
helper.setTo(username);
helper.setText(notifies.getText());

sender.send(message);
}    
    
}
