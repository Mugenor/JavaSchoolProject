package javaschool.controller.rest;

import java.security.Principal;
import java.util.List;
import javaschool.controller.dtoentity.NewDepartureDTO;
import javaschool.controller.dtoentity.TrainInfo;
import javaschool.controller.dtoentity.TripDTO;
import javaschool.controller.dtoentity.TripInfo;
import javaschool.controller.dtoentity.TripUpdate;
import javaschool.service.api.PassengerService;
import javaschool.service.api.RabbitService;
import javaschool.service.api.TripService;
import javax.validation.Valid;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/trip", produces = MediaType.APPLICATION_JSON_VALUE)
public class TripController {
    private TripService tripService;
    private PassengerService passengerService;
    private RabbitService rabbitService;

    @Autowired
    public TripController(TripService tripService, PassengerService passengerService,
                          RabbitService  rabbitService) {
        this.tripService = tripService;
        this.passengerService = passengerService;
        this.rabbitService = rabbitService;
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

    @GetMapping("/{tripId}")
    public TripDTO getTripById(@PathVariable Integer tripId) {
        return tripService.findById(tripId);
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public TripDTO addNewTrip(@Valid @RequestBody List<NewDepartureDTO> trip) {
        TripDTO savedTrip = tripService.save(trip);
        rabbitService.convertAndSend(new TripUpdate(TripUpdate.CREATE, savedTrip));
        return savedTrip;
    }

    @GetMapping(path = "/occupied-seats/{tripId}/{departureFromIndex}/{departureToIndex}")
    public TrainInfo getOccupiedSeatsByTripIdAndDepartureBounds(@PathVariable Integer tripId,
                                                                @PathVariable Integer departureFromIndex,
                                                                @PathVariable Integer departureToIndex) {
        return tripService.getDepartureInfoByTripIdAndDepartureBounds(tripId, departureFromIndex, departureToIndex);
    }

    @GetMapping(path = "/info/{tripId}/{departureFromIndex}/{departureToIndex}")
    public TripInfo getTripInfo(@PathVariable Integer tripId,
                                @PathVariable Integer departureFromIndex,
                                @PathVariable Integer departureToIndex) {
        return tripService.getTripInfo(tripId, departureFromIndex, departureToIndex);
    }

    @GetMapping("/registered/{tripId}")
    public Boolean isRegistered(@PathVariable Integer tripId, Principal principal) {
        return passengerService.isRegistered(tripId, principal.getName());
    }

    @GetMapping("/timetable/{stationTitle}")
    public List<TripDTO> getTimetableOfStation(@PathVariable String stationTitle) {
        return tripService.findAvailableByStationTitle(stationTitle);
    }

    @GetMapping("/find")
    public List<TripDTO> findTrips(@RequestParam String departureStation,
                                   @RequestParam String arrivalStation,
                                   @RequestParam Long dateTimeFrom,
                                   @RequestParam Long dateTimeTo) {
        return tripService.findFromToBetween(departureStation, arrivalStation,
                new LocalDateTime(dateTimeFrom), new LocalDateTime(dateTimeTo));
    }

    @GetMapping("/find/{maxTransferCount}")
    public List<List<TripDTO>> findTripsWithTransfers(@RequestParam String departureStation,
                                                      @RequestParam String arrivalStation,
                                                      @RequestParam Long dateTimeFrom,
                                                      @RequestParam Long dateTimeTo,
                                                      @PathVariable Integer maxTransferCount) {
        return tripService.findFromToBetweenWithTransfers(departureStation, arrivalStation,
                new LocalDateTime(dateTimeFrom), new LocalDateTime(dateTimeTo), maxTransferCount);

    }

    @DeleteMapping("/{tripId}")
    public void deleteTrip(@PathVariable Integer tripId) {
        TripDTO deletedTrip = tripService.deleteTrip(tripId);
        rabbitService.convertAndSend(new TripUpdate(TripUpdate.DELETE, deletedTrip));
    }
}
