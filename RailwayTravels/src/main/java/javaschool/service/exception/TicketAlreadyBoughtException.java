package javaschool.service.exception;

public class TicketAlreadyBoughtException extends RuntimeException {
    public TicketAlreadyBoughtException(String s) {
    }

    public TicketAlreadyBoughtException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketAlreadyBoughtException() {
    }
}
