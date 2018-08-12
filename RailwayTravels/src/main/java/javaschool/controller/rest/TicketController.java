package javaschool.controller.rest;

import java.security.Principal;
import javaschool.controller.dtoentity.TicketBuyDTO;
import javaschool.service.api.PassengerService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {
    private PassengerService passengerService;

    @Autowired
    public TicketController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/client/tickets/buy")
    public void buyTicket(@Valid @RequestBody TicketBuyDTO ticketBuy, Principal principal) {
        passengerService.buyTicket(principal.getName(),
                ticketBuy.getTripId(),
                ticketBuy.getDepartureFromIndex(),
                ticketBuy.getDepartureToIndex(),
                ticketBuy.getCoachNumber(),
                ticketBuy.getSeatNum());
    }

    @GetMapping
    public void getPassengerTickets(Principal principal) {

    }
}
