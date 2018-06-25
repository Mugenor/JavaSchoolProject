package javaschool.controller.rest;

import javaschool.entity.Passenger;
import javaschool.service.api.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class PassengerController {
    private PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @RequestMapping(path = "/passenger", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Passenger> getAllPassengers(){
        List<Passenger> passengers = new LinkedList<Passenger>();
        passengers.add(new Passenger("Ilya","Chernov"));

        return passengers;
    }
}
