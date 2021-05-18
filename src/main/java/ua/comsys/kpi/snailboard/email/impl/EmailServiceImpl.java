package ua.comsys.kpi.snailboard.email.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import ua.comsys.kpi.snailboard.email.EmailService;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendEmail(String recipient, String messageText, String subject) {
//        var message = new SimpleMailMessage();
//        message.setFrom("noreply@snailboard.com");
//        message.setTo(recipient);
//        message.setSubject(subject);
//        message.setText(messageText);
//        emailSender.send(message);
          System.out.println(messageText + " to " + recipient);
    }

}
