package javaschool.controller.rest;

import java.util.List;
import javaschool.controller.dtoentity.PassengerWithoutTickets;
import javaschool.service.api.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/passenger", produces = APPLICATION_JSON_VALUE)
public class PassengerController {
    private PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping
    public List<PassengerWithoutTickets> getAllPassengers(){
        return passengerService.findAllPassengers();
    }

    @GetMapping("/{tripId}/{from}/{to}")
    public List<PassengerWithoutTickets> getAllPassengersRegisteredOnTrip(@PathVariable Integer tripId,
                                                                          @PathVariable Integer from, @PathVariable Integer to) {
        return passengerService.findAllPassengersByTripIdAndDepartureIndexBounds(tripId, from, to);
    }

}
