package javaschool.controller.advice;

import javaschool.service.exception.NoSuchEntityException;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
}
