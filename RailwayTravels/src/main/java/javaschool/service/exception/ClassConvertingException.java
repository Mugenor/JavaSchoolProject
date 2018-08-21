package javaschool.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * The type Class converting exception.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClassConvertingException extends RuntimeException {
    /**
     * Instantiates a new Class converting exception.
     */
    public ClassConvertingException() {
    }

    /**
     * Instantiates a new Class converting exception.
     *
     * @param message the message
     */
    public ClassConvertingException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Class converting exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ClassConvertingException(String message, Throwable cause) {
        super(message, cause);
    }
}
