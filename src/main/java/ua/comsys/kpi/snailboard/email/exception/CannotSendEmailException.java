package ua.comsys.kpi.snailboard.email.exception;

public class CannotSendEmailException extends RuntimeException{
    public CannotSendEmailException(String message) {
        super(message);
    }
}
