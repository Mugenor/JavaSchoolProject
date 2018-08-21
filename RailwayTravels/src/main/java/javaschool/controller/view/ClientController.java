package javaschool.controller.view;

import javaschool.service.exception.NoSuchEntityException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * The type Client controller.
 */
@Controller
public class ClientController {
    private static final String CLIENT_VIEW = "/client";
    private static final String CLIENT_ERROR_VIEW = "/client";


    /**
     * Gets client page.
     *
     * @return the client page
     */
    @GetMapping(path = {"/client", "/client/{*}", "/client/{*}/{*}",
            "/client/{*}/{*}/{*}", "client/{*}/{*}/{*}/{*}"})
    public ModelAndView getClientPage() {
        return new ModelAndView(CLIENT_VIEW);
    }

    /**
     * Handle no departure exception string.
     *
     * @param model the model
     * @param exc   the exc
     * @return the string
     */
    @ExceptionHandler(NoSuchEntityException.class)
    public String handleNoDepartureException(RedirectAttributes model, Exception exc) {
        model.addFlashAttribute("error", exc.getMessage());
        return "redirect:" + CLIENT_ERROR_VIEW;
    }
}
