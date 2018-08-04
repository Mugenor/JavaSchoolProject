package javaschool.controller.rest;

import java.util.List;
import javaschool.controller.dtoentity.TripDTO;
import javaschool.service.api.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/trip", produces = MediaType.APPLICATION_JSON_VALUE)
public class TripController {
    private TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    public List<TripDTO> getAllTrips() {
        return tripService.findAll();
    }

    @GetMapping("/today")
    public List<TripDTO> getTodayTrips() {
        return tripService.findAllToday();
    }

    @GetMapping("/available")
    public List<TripDTO> getAllAvailableTrips() {
        return tripService.findAllAvailable();
    }
}
