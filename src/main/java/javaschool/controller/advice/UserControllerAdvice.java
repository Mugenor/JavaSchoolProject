package javaschool.controller.advice;

import javaschool.service.exception.EntityAlreadyExistsException;
import javax.validation.ValidationException;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserControllerAdvice {
    private static final Logger log = Logger.getLogger(UserControllerAdvice.class);
}
