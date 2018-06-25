package javaschool.service.impl;

import javaschool.dao.api.DepartureDAO;
import javaschool.dao.api.PassengerDAO;
import javaschool.dao.api.TicketDAO;
import javaschool.entity.Departure;
import javaschool.entity.Passenger;
import javaschool.entity.Ticket;
import javaschool.service.api.PassengerService;
import javaschool.service.exception.NoSiteOnDepartureException;
import javaschool.service.exception.PassengerRegisteredException;
import javaschool.service.exception.TicketAlreadyBoughtException;
import javaschool.service.exception.TooLateForBuyingTicketException;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

@Service()
public class PassengerServiceImpl implements PassengerService {
    private PassengerDAO passengerDAO;
    private TicketDAO ticketDAO;
    private DepartureDAO departureDAO;

    @Autowired
    public PassengerServiceImpl(PassengerDAO passengerDAO, TicketDAO ticketDAO, DepartureDAO departureDAO) {
        this.passengerDAO = passengerDAO;
        this.ticketDAO = ticketDAO;
        this.departureDAO = departureDAO;
    }


    @Transactional(readOnly = true)
    public List<Passenger> getAllPassengers() {
        return passengerDAO.findAll();
    }

    @Transactional
    public void save(Passenger passenger) {
        passengerDAO.save(passenger);
    }

    @Override
    @Transactional(readOnly = true)
    public Passenger getById(Integer id) {
        return passengerDAO.findById(id);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = {NoSiteOnDepartureException.class, PassengerRegisteredException.class,
            TooLateForBuyingTicketException.class, NoResultException.class})
    public void buyTicket(Integer passengerId, Integer ticketId) {
        Passenger passenger = passengerDAO.findById(passengerId);
        Ticket ticket = ticketDAO.findById(ticketId);
        Departure departure = ticket.getDeparture();
        if(ticket.getPassenger() != null) {
            throw new TicketAlreadyBoughtException("This ticket was bought by someone else!");
        }

        if(LocalDateTime.now().plusMinutes(10).isAfter(departure.getDateTimeFrom())) {
            throw new TooLateForBuyingTicketException("You must buy a ticket earlier than 10 minutes before departure!");
        }

        if (departure.getFreeSitsCount() <= 0) {
            throw new NoSiteOnDepartureException("There is no free sits on this departure!");
        }

        for (Ticket t : departure.getTickets()) {
            if (t.getPassenger() != null && t.getPassenger().equals(passenger)) {
                throw new PassengerRegisteredException("Passenger " + passenger.getName() + " " + passenger.getSurname() +
                        " already registered on this departure!");
            }
        }

        ticket.setPassenger(passenger);
        passenger.getTickets().add(ticket);
        departure.setFreeSitsCount(departure.getFreeSitsCount() - 1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Passenger> findAllPassengersByDepartureId(Integer departureId) {
        return passengerDAO.findAllPassengersByDepartureId(departureId);
    }
}
