package javaschool.controller.view;

import javaschool.service.api.DepartureService;
import javaschool.service.api.StationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(path = "/client")
public class ClientController {
    private static final Logger log = Logger.getLogger(ClientController.class);
    private static final String CLIENT_VIEW = "/client";
    private static final String SELECT_SEAT_VIEW = "/select-seat";
    private static final String DEPARTURES_VIEW = "/departures";
    private static final String FIND_DEPARTURE_VIEW = "/find-departure";
    private static final String STATIONS_VIEW = "/stations";

    private DepartureService departureService;
    private StationService stationService;

    @Autowired
    public ClientController(DepartureService departureService, StationService stationService) {
        this.departureService = departureService;
        this.stationService = stationService;
    }

    @GetMapping
    public ModelAndView getClientPage() {
        return new ModelAndView(CLIENT_VIEW);
    }

    @GetMapping("/select/{departureId}")
    public ModelAndView getSelectSeatView(@PathVariable int departureId) {
        ModelAndView modelAndView = new ModelAndView(SELECT_SEAT_VIEW);
        modelAndView.addObject("departure", departureService.findByIdRaw(departureId, true, true));
        return modelAndView;
    }

    @GetMapping("/all-departures")
    public ModelAndView getAllDeparturesView(){
        ModelAndView modelAndView = new ModelAndView(DEPARTURES_VIEW);
        modelAndView.addObject("departures", departureService.findAll(true, false));
        return modelAndView;
    }

    @GetMapping("/departures/{stationName}")
    public ModelAndView getAllDeparturesByStationName(@PathVariable String stationName){
        return new ModelAndView(DEPARTURES_VIEW).addObject("departures", departureService.findByStationTitle(stationName, true, false));
    }

    @GetMapping("/find-departure")
    public String getFindDepartureView() {
        return FIND_DEPARTURE_VIEW;
    }

    @GetMapping("/stations")
    public ModelAndView getStationsView() {
        return new ModelAndView(STATIONS_VIEW).addObject("stations", stationService.findAllStationNames());
    }
}
