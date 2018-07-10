package javaschool.controller.advice;

import java.io.IOException;
import javaschool.service.exception.NoSiteOnDepartureException;
import javaschool.service.exception.NoSuchEntityException;
import javaschool.service.exception.PassengerRegisteredException;
import javaschool.service.exception.TicketAlreadyBoughtException;
import javaschool.service.exception.TooLateForBuyingTicketException;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ClientControllerAdvice {
    private static final Logger log = Logger.getLogger(ClientControllerAdvice.class);
    private static final String CLIENT_ERROR_VIEW = "/client";

    @ExceptionHandler(NoSuchEntityException.class)
    public String handleNoDepartureException(RedirectAttributes model, Exception exc) {
        model.addFlashAttribute("error", exc.getMessage());
        return "redirect:" + CLIENT_ERROR_VIEW;
    }

    @ExceptionHandler({TooLateForBuyingTicketException.class, TicketAlreadyBoughtException.class,
            PassengerRegisteredException.class, NoSiteOnDepartureException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public String handleTicketBuyExceptions(Exception exc) throws IOException {
        return exc.getMessage();
    }
}
