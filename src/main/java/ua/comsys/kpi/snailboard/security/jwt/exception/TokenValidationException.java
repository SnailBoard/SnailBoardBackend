package ua.comsys.kpi.snailboard.security.jwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class TokenValidationException extends RuntimeException{

    public TokenValidationException(String message) {
        super(message);
    }

}
