package javaschool.controller.view;

import javaschool.service.api.DepartureService;
import javaschool.service.exception.NoSuchEntityException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class ClientController {
    private static final Logger log = Logger.getLogger(ClientController.class);
    private static final String CLIENT_VIEW = "/client";
    private static final String SELECT_SEAT_VIEW = "/select-seat";
    private static final String ALL_DEPARTURES_VIEW = "/all-departures";

    private DepartureService departureService;

    @Autowired
    public ClientController(DepartureService departureService) {
        this.departureService = departureService;
    }

    @GetMapping("/client")
    public ModelAndView getClientPage() {
        return new ModelAndView(CLIENT_VIEW);
    }

    @GetMapping("/select/{departureId}")
    public ModelAndView getSelectSeatView(@PathVariable int departureId) {
        ModelAndView modelAndView = new ModelAndView(SELECT_SEAT_VIEW);
        modelAndView.addObject("departure", departureService.findById(departureId));
        return modelAndView;
    }

    @GetMapping("/all-departures")
    public String getAllDeparturesView(){
        return ALL_DEPARTURES_VIEW;
    }
}
