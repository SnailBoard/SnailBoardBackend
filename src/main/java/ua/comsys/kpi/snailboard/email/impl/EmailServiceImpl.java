package ua.comsys.kpi.snailboard.email.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.comsys.kpi.snailboard.email.EmailService;
import ua.comsys.kpi.snailboard.email.exception.CannotSendEmailException;
import ua.comsys.kpi.snailboard.utils.files.FileManager;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

@Component
public class EmailServiceImpl implements EmailService {

    @Value("${email.from}")
    private String SNAILBOARD_EMAIL;
    @Value("${email.password}")
    private String SNAILBOARD_PASSWORD;

    private static final String HTML_TEXT = "text/html";
    private String template;

    @Override
    public void sendEmail(String recipient, Map<String, String> templateProps, String templateName, String subject) {
        Message message = createMessage();
        template = fillProps(templateProps, FileManager.readPropFile(templateName));

        try {
            message.setFrom(new InternetAddress(SNAILBOARD_EMAIL, false));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setContent(template, HTML_TEXT);
            message.setSentDate(new Date());

            Transport.send(message);
        } catch (MessagingException e) {
            throw new CannotSendEmailException(e.getMessage());
        }
    }

    private Message createMessage() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SNAILBOARD_EMAIL, SNAILBOARD_PASSWORD);
            }
        });

        return new MimeMessage(session);
    }

    private String fillProps(Map<String, String> props, String template) {
        String newTemplate = template;
        for (Map.Entry<String, String> entry : props.entrySet()) {
            newTemplate = newTemplate.replaceAll("\\{" + entry.getKey() + "}", entry.getValue());
        }
        return newTemplate;
    }

    //for testing
    @Deprecated
    public String getTemplate() {
        return template;
    }
}
