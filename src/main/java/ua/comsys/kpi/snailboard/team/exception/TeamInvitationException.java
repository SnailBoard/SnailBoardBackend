package ua.comsys.kpi.snailboard.team.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TeamInvitationException extends RuntimeException {
    private final String message;

    public TeamInvitationException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
