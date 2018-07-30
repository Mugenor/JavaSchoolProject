package javaschool.service.exception;

public class TooLateForBuyingTicketException extends RuntimeException {
    public TooLateForBuyingTicketException() {
    }

    public TooLateForBuyingTicketException(String message) {
        super(message);
    }

    public TooLateForBuyingTicketException(String message, Throwable cause) {
        super(message, cause);
    }
}
