package ua.comsys.kpi.snailboard.email;

public interface EmailService {
    void sendEmail(String recipient, String messageText, String subject);
}
