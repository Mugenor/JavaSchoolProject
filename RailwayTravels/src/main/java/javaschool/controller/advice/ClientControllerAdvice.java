package javaschool.controller.advice;

import java.io.IOException;
import javaschool.service.exception.NoSiteOnDepartureException;
import javaschool.service.exception.NoSuchEntityException;
import javaschool.service.exception.PassengerRegisteredException;
import javaschool.service.exception.StationEqualsException;
import javaschool.service.exception.TicketAlreadyBoughtException;
import javaschool.service.exception.TooLateForBuyingTicketException;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ClientControllerAdvice {
    private static final Logger log = Logger.getLogger(ClientControllerAdvice.class);



    @ExceptionHandler({TooLateForBuyingTicketException.class, TicketAlreadyBoughtException.class,
            PassengerRegisteredException.class, NoSiteOnDepartureException.class,
            StationEqualsException.class, NoSuchEntityException.class})
    public ResponseEntity<String> handleTicketBuyExceptions(Exception exc) throws IOException {
        return ResponseEntity.badRequest().body(exc.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException exc) {
        log.error("Exception while trying to save something: ", exc);
        return ResponseEntity.badRequest().body(exc.getMessage());
    }
}
