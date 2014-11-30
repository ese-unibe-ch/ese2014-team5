package org.sample.controller.service;

import java.text.SimpleDateFormat;
import java.util.Properties;
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
 * This class sends the E-mails to the accounts to announce an notification
 *
 */
public class EmailSender {

    public void GenerateAnEmail(Notifies notes) throws MessagingException {

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.host", "smtp.gmail.com");
        props.put("mail.debug", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        /*The server configuration, over Gmail as smtpservice*/
        sender.setJavaMailProperties(props);
        sender.setProtocol("smtp");
        sender.setHost("smtp.gmail.com");
        sender.setUsername("eseteam5@gmail.com");
        sender.setPassword("^RX!/T'wdF;5%7R%KC");
        sender.setPort(465);

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String date = dateFormat.format(notes.getDate());
        String text = "";
        String type = "";

        try {
            switch (notes.getNotetype()) {
                case BOOKMARK:
                    text = "Bookmarked Ad " + notes.getBookmark().getAd().getTitle() + " has changed - " + date + "";
                    break;
                case MESSAGE:
                    text = notes.getText() + " - " + date + "";
                    break;
                case ENQUIRY:
                    text = "New enquiry received from " + notes.getFromUser().getUsername() + " - " + date + "";
                    break;
                case SEARCHMATCH:
                    text = "New matching ad for your search! - " + date + "";
                    break;
                case INVITATION:
                    text = "New Invitation for your enquiry! - " + date + "";
                    break;
                default:
                    text = notes.getText() + " - " + date + "";
            }
        
        
        /*The real message*/
        String username = notes.getToUser().getUsername();
        type = notes.getNotetype().toString();
        helper.setTo(username);
        helper.setFrom(username);
        helper.setSubject(type);
        helper.setText(text);

        sender.send(message);
        
        } catch (NullPointerException e) {

        }

    }

}
