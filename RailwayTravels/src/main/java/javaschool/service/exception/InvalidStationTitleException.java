package javaschool.service.exception;

/**
 * The type Invalid station title exception.
 */
public class InvalidStationTitleException extends RuntimeException {
    /**
     * Instantiates a new Invalid station title exception.
     */
    public InvalidStationTitleException() {
    }

    /**
     * Instantiates a new Invalid station title exception.
     *
     * @param message the message
     */
    public InvalidStationTitleException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Invalid station title exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public InvalidStationTitleException(String message, Throwable cause) {
        super(message, cause);
    }
}
