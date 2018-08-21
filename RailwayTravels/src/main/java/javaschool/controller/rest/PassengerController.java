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

/**
 * The type Passenger controller.
 */
@RestController
@RequestMapping(path = "/passenger", produces = APPLICATION_JSON_VALUE)
public class PassengerController {
    private PassengerService passengerService;

    /**
     * Instantiates a new Passenger controller.
     *
     * @param passengerService the passenger service
     */
    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    /**
     * Get all passengers list.
     *
     * @return the list
     */
    @GetMapping
    public List<PassengerWithoutTickets> getAllPassengers(){
        return passengerService.findAllPassengers();
    }

    /**
     * Gets all passengers registered on trip.
     *
     * @param tripId the trip id
     * @param from   the from
     * @param to     the to
     * @return the all passengers registered on trip
     */
    @GetMapping("/{tripId}/{from}/{to}")
    public List<PassengerWithoutTickets> getAllPassengersRegisteredOnTrip(@PathVariable Integer tripId,
                                                                          @PathVariable Integer from, @PathVariable Integer to) {
        return passengerService.findAllPassengersByTripIdAndDepartureIndexBounds(tripId, from, to);
    }

}
