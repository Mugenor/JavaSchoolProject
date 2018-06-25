package javaschool.service.exception;

public class NoSiteOnDepartureException extends RuntimeException {
    public NoSiteOnDepartureException() {
    }

    public NoSiteOnDepartureException(String message) {
        super(message);
    }
}
