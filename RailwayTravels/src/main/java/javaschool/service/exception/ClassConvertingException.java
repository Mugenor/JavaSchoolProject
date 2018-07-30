package javaschool.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
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
