package javaschool.controller.rest;

import java.util.List;
import javaschool.entity.Departure;
import javaschool.service.api.DepartureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/departure", produces = MediaType.APPLICATION_JSON_VALUE)
public class DepartureController {
    private DepartureService departureService;

    @Autowired
    public DepartureController(DepartureService departureService) {
        this.departureService = departureService;
    }

    @GetMapping
    public List<Departure> getAllDepartures() {
        return departureService.findAll();
    }
}
