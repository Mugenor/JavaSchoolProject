package javaschool.controller.rest;

import java.security.Principal;
import java.util.List;
import javaschool.controller.dtoentity.TicketBuyDTO;
import javaschool.controller.dtoentity.TicketDTO;
import javaschool.service.api.PassengerService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client/tickets")
public class TicketController {
    private PassengerService passengerService;

    @Autowired
    public TicketController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping(path = "/buy")
    public void buyTicket(@Valid @RequestBody TicketBuyDTO ticketBuy, Principal principal) {
        passengerService.buyTicket(principal.getName(),
                ticketBuy.getTripId(),
                ticketBuy.getDepartureFromIndex(),
                ticketBuy.getDepartureToIndex(),
                ticketBuy.getCoachNumber(),
                ticketBuy.getSeatNum());
    }

    @GetMapping
    public List<TicketDTO> getPassengerTickets(Principal principal) {
        return passengerService.getPassengerTickets(principal.getName());
    }

    @DeleteMapping(path = "/{ticketId}")
    public void returnTicket(@PathVariable Integer ticketId, Principal principal) {
        passengerService.returnTicket(principal.getName(), ticketId);
    }

}
