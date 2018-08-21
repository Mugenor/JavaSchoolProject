package javaschool.service.exception;

/**
 * The type Station equals exception.
 */
public class StationEqualsException extends RuntimeException {
    /**
     * Instantiates a new Station equals exception.
     */
    public StationEqualsException() {
    }

    /**
     * Instantiates a new Station equals exception.
     *
     * @param message the message
     */
    public StationEqualsException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Station equals exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public StationEqualsException(String message, Throwable cause) {
        super(message, cause);
    }
}
