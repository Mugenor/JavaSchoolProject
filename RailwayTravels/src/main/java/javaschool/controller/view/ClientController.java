package javaschool.controller.view;

import javaschool.service.api.DepartureService;
import javaschool.service.api.StationService;
import javaschool.service.exception.NoSuchEntityException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class ClientController {
    private static final Logger log = Logger.getLogger(ClientController.class);
    private static final String CLIENT_VIEW = "/client";
    private static final String CLIENT_ERROR_VIEW = "/client";


    private DepartureService departureService;
    private StationService stationService;

    @Autowired
    public ClientController(DepartureService departureService, StationService stationService) {
        this.departureService = departureService;
        this.stationService = stationService;
    }

    @RequestMapping(path = {"/client", "/client/{*}", "/client/{*}/{*}",
            "/client/{*}/{*}/{*}", "client/{*}/{*}/{*}/{*}"}, method = RequestMethod.GET)
    public ModelAndView getClientPage() {
        return new ModelAndView(CLIENT_VIEW);
    }

    @ExceptionHandler(NoSuchEntityException.class)
    public String handleNoDepartureException(RedirectAttributes model, Exception exc) {
        model.addFlashAttribute("error", exc.getMessage());
        return "redirect:" + CLIENT_ERROR_VIEW;
    }
}
