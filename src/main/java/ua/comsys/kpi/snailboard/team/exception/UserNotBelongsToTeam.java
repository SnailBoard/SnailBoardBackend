package ua.comsys.kpi.snailboard.team.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User not belong to team")
public class UserNotBelongsToTeam extends RuntimeException {
}
