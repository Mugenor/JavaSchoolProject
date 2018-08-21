package javaschool.controller.rest;

import com.lowagie.text.DocumentException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javaschool.controller.dtoentity.TicketBuyDTO;
import javaschool.controller.dtoentity.TicketDTO;
import javaschool.controller.dtoentity.TicketUpdateDTO;
import javaschool.entity.Ticket;
import javaschool.service.api.MailSender;
import javaschool.service.api.PassengerService;
import javaschool.service.api.StompMessageSender;
import javaschool.service.impl.TicketToPDFTicketConverter;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.util.ByteArrayDataSource;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Ticket controller.
 */
@RestController
@RequestMapping("/client/tickets")
public class TicketController {
    private PassengerService passengerService;
    private TicketToPDFTicketConverter ticketToPDFTicketConverter;
    private MailSender mailSender;
    private StompMessageSender stompMessageSender;

    /**
     * Instantiates a new Ticket controller.
     *
     * @param passengerService           the passenger service
     * @param ticketToPDFTicketConverter the ticket to pdf ticket converter
     * @param mailSender                 the mail sender
     * @param stompMessageSender         the stomp message sender
     */
    @Autowired
    public TicketController(PassengerService passengerService,
                            TicketToPDFTicketConverter ticketToPDFTicketConverter,
                            MailSender mailSender,
                            StompMessageSender stompMessageSender) {
        this.passengerService = passengerService;
        this.ticketToPDFTicketConverter = ticketToPDFTicketConverter;
        this.mailSender = mailSender;
        this.stompMessageSender = stompMessageSender;
    }

    /**
     * Buy ticket.
     *
     * @param ticketBuy the ticket buy
     * @param principal the principal
     * @throws IOException        the io exception
     * @throws DocumentException  the document exception
     * @throws MessagingException the messaging exception
     */
    @PostMapping(path = "/buy")
    public void buyTicket(@Valid @RequestBody TicketBuyDTO ticketBuy, Principal principal) throws IOException, DocumentException,
            MessagingException {
        Ticket ticket = passengerService.buyTicket(principal.getName(),
                ticketBuy.getTripId(),
                ticketBuy.getDepartureFromIndex(),
                ticketBuy.getDepartureToIndex(),
                ticketBuy.getCoachNumber(),
                ticketBuy.getSeatNum());
        stompMessageSender.sendToDefaultPrefix("/" + ticketBuy.getTripId(),
                new TicketUpdateDTO(TicketUpdateDTO.BUY, ticketBuy.getDepartureFromIndex(), ticketBuy.getDepartureToIndex(),
                        ticketBuy.getCoachNumber(), ticketBuy.getSeatNum()));
        Map<String, DataSource> attachment = new TreeMap<>();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ticketToPDFTicketConverter.writePDFTicket(outputStream, ticket);
        DataSource dataSource = new ByteArrayDataSource(outputStream.toByteArray(), "application/pdf");

        attachment.put("ticket.pdf", dataSource);
        mailSender.sendMail(ticket.getPassenger().getUser().getEmail(), "ticketMessageTemplate.html",
                "Ticket", attachment);
    }

    /**
     * Gets passenger tickets.
     *
     * @param principal the principal
     * @return the passenger tickets
     */
    @GetMapping
    public List<TicketDTO> getPassengerTickets(Principal principal) {
        return passengerService.getPassengerTickets(principal.getName());
    }

    /**
     * Return ticket.
     *
     * @param ticketId  the ticket id
     * @param principal the principal
     */
    @DeleteMapping(path = "/{ticketId}")
    public void returnTicket(@PathVariable Integer ticketId, Principal principal) {
        TicketBuyDTO ticket = passengerService.returnTicket(principal.getName(), ticketId);
        stompMessageSender.sendToDefaultPrefix("/" + ticket.getTripId(),
                new TicketUpdateDTO(TicketUpdateDTO.RETURN, ticket.getDepartureFromIndex(),
                        ticket.getDepartureToIndex(), ticket.getCoachNumber(), ticket.getSeatNum()));
    }

}
