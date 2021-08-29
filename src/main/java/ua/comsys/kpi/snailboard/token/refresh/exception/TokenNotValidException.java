package ua.comsys.kpi.snailboard.token.refresh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "token is not valid for current user")
public class TokenNotValidException extends RuntimeException {
}
