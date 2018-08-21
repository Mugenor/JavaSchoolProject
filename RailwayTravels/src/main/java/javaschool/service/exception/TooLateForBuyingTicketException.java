package javaschool.service.exception;

/**
 * The type Too late for buying ticket exception.
 */
public class TooLateForBuyingTicketException extends RuntimeException {
    /**
     * Instantiates a new Too late for buying ticket exception.
     */
    public TooLateForBuyingTicketException() {
    }

    /**
     * Instantiates a new Too late for buying ticket exception.
     *
     * @param message the message
     */
    public TooLateForBuyingTicketException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Too late for buying ticket exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public TooLateForBuyingTicketException(String message, Throwable cause) {
        super(message, cause);
    }
}
