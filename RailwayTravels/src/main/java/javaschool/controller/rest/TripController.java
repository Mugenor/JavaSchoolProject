package javaschool.controller.rest;

import java.util.List;
import javaschool.controller.dtoentity.DepartureDTO;
import javaschool.controller.dtoentity.NewDepartureDTO;
import javaschool.controller.dtoentity.TripDTO;
import javaschool.service.api.TripService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public TripDTO addNewTrip(@Valid @RequestBody List<NewDepartureDTO> trip) {
        return tripService.saveWithNotification(trip);
    }
}
