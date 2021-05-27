package ua.comsys.kpi.snailboard.token.refresh.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TokenNotValidException extends RuntimeException {
}
