package javaschool.service.exception;

public class StationEqualsException extends RuntimeException {
    public StationEqualsException() {
    }

    public StationEqualsException(String message) {
        super(message);
    }

    public StationEqualsException(String message, Throwable cause) {
        super(message, cause);
    }
}
