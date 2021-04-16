package ua.comsys.kpi.snailboard.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Such email is already used")
public class UserExistsException extends RuntimeException {
}
