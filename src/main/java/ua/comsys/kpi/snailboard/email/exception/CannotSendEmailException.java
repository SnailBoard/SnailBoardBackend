package ua.comsys.kpi.snailboard.email.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "cant send email")
public class CannotSendEmailException extends RuntimeException{
    public CannotSendEmailException(String message) {
        super(message);
    }
}
