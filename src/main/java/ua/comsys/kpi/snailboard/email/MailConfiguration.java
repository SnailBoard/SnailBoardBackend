package ua.comsys.kpi.snailboard.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration {

    @Value("${spring.mail.host}")
    private String mailHost;
    @Value("${spring.mail.password}")
    private String mailPassword;
    @Value("${spring.mail.username}")
    private String mailUsername;
    @Value("${spring.mail.properties.mail.transport.protocol}")
    private String mailProtocol;

    @Bean
    public JavaMailSender getJavaMailSender() {
        var mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(587);

        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);

        var props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", mailProtocol);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
