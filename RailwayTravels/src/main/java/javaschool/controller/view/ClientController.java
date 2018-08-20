package javaschool.controller.view;

import javaschool.service.exception.NoSuchEntityException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class ClientController {
    private static final String CLIENT_VIEW = "/client";
    private static final String CLIENT_ERROR_VIEW = "/client";


    @GetMapping(path = {"/client", "/client/{*}", "/client/{*}/{*}",
            "/client/{*}/{*}/{*}", "client/{*}/{*}/{*}/{*}"})
    public ModelAndView getClientPage() {
        return new ModelAndView(CLIENT_VIEW);
    }

    @ExceptionHandler(NoSuchEntityException.class)
    public String handleNoDepartureException(RedirectAttributes model, Exception exc) {
        model.addFlashAttribute("error", exc.getMessage());
        return "redirect:" + CLIENT_ERROR_VIEW;
    }
}
