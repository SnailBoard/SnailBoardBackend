package ua.comsys.kpi.snailboard.column.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Position for board is not unique")
public class NotUniquePositionException extends RuntimeException {
}
