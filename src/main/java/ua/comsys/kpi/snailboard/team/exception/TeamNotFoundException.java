package ua.comsys.kpi.snailboard.team.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Team not found")
public class TeamNotFoundException extends RuntimeException {
}
