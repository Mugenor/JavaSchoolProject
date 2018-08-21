package javaschool.service.exception;

/**
 * The type Passenger registered exception.
 */
public class PassengerRegisteredException extends RuntimeException {
    /**
     * Instantiates a new Passenger registered exception.
     *
     * @param message the message
     */
    public PassengerRegisteredException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Passenger registered exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public PassengerRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Passenger registered exception.
     */
    public PassengerRegisteredException() {
    }
}
