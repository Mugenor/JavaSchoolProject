package javaschool.service.exception;

public class InvalidStationTitleException extends RuntimeException {
    public InvalidStationTitleException() {
    }

    public InvalidStationTitleException(String message) {
        super(message);
    }

    public InvalidStationTitleException(String message, Throwable cause) {
        super(message, cause);
    }
}
