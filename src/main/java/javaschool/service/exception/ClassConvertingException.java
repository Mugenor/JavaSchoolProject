package javaschool.service.exception;

public class ClassConvertingException extends RuntimeException {
    public ClassConvertingException() {
    }

    public ClassConvertingException(String message) {
        super(message);
    }

    public ClassConvertingException(String message, Throwable cause) {
        super(message, cause);
    }
}
