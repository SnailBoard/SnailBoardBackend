package ua.comsys.kpi.snailboard.column.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Column with id not found")
public class ColumnNotFoundException extends RuntimeException{
}
