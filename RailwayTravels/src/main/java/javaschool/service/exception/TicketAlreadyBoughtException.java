package javaschool.service.exception;

/**
 * The type Ticket already bought exception.
 */
public class TicketAlreadyBoughtException extends RuntimeException {
    /**
     * Instantiates a new Ticket already bought exception.
     *
     * @param s the s
     */
    public TicketAlreadyBoughtException(String s) {
    }

    /**
     * Instantiates a new Ticket already bought exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public TicketAlreadyBoughtException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Ticket already bought exception.
     */
    public TicketAlreadyBoughtException() {
    }
}
