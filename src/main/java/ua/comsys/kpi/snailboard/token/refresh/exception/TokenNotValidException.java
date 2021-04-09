package ua.comsys.kpi.snailboard.token.refresh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenNotValidException extends RuntimeException {
}
