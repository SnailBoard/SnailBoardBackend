package ua.comsys.kpi.snailboard.email.impl;

import org.springframework.stereotype.Component;
import ua.comsys.kpi.snailboard.email.EmailService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Component
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendEmail(String recipient, String messageText, String subject) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("snailboardinc@gmail.com", "snailboard123");
            }
        });
        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress("snailboardinc@gmail.com", false));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            msg.setSubject(subject);
            msg.setContent(messageText, "text/html");
            msg.setSentDate(new Date());

            Transport.send(msg);

            System.out.println(messageText + " to " + recipient);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
