package ua.comsys.kpi.snailboard.board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Board not found")
public class BoardNotFoundException extends RuntimeException {
}

