package javaschool.service.exception;

/**
 * The type Entity already exists exception.
 */
public class EntityAlreadyExistsException extends RuntimeException {
    /**
     * Instantiates a new Entity already exists exception.
     */
    public EntityAlreadyExistsException() {
    }

    /**
     * Instantiates a new Entity already exists exception.
     *
     * @param message the message
     */
    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Entity already exists exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public EntityAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
