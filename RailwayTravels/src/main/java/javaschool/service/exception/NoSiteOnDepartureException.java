package javaschool.service.exception;

/**
 * The type No site on departure exception.
 */
public class NoSiteOnDepartureException extends RuntimeException {
    /**
     * Instantiates a new No site on departure exception.
     */
    public NoSiteOnDepartureException() {
    }

    /**
     * Instantiates a new No site on departure exception.
     *
     * @param message the message
     */
    public NoSiteOnDepartureException(String message) {
        super(message);
    }
}
