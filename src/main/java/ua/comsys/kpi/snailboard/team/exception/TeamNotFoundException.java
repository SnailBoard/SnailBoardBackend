package ua.comsys.kpi.snailboard.team.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Team not found")
public class TeamNotFoundException extends RuntimeException{
}
