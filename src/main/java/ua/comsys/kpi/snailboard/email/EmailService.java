package ua.comsys.kpi.snailboard.email;

import java.util.Map;

public interface EmailService {
    void sendEmail(String recipient, Map<String, String> templateProps, String templateName, String subject);
}
