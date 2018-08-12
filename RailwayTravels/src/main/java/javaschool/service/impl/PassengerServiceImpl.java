package javaschool.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javaschool.controller.dtoentity.PassengerWithoutTickets;
import javaschool.controller.dtoentity.TicketDTO;
import javaschool.dao.api.DepartureDAO;
import javaschool.dao.api.OccupiedSeatDAO;
import javaschool.dao.api.PassengerDAO;
import javaschool.dao.api.TicketDAO;
import javaschool.dao.api.TripDAO;
import javaschool.entity.Departure;
import javaschool.entity.OccupiedSeat;
import javaschool.entity.Passenger;
import javaschool.entity.Ticket;
import javaschool.entity.Trip;
import javaschool.entity.id.SeatId;
import javaschool.service.api.PassengerService;
import javaschool.service.converter.PassengerToPassengerWithoutTicketsConverter;
import javaschool.service.exception.NoSiteOnDepartureException;
import javaschool.service.exception.NoSuchEntityException;
import javaschool.service.exception.TicketAlreadyBoughtException;
import javaschool.service.exception.TooLateForBuyingTicketException;
import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PassengerServiceImpl implements PassengerService {
    private static final Logger log = Logger.getLogger(PassengerServiceImpl.class);

    private PassengerDAO passengerDAO;
    private DepartureDAO departureDAO;
    private TripDAO tripDAO;
    private OccupiedSeatDAO occupiedSeatDAO;
    private TicketDAO ticketDAO;
    private PassengerToPassengerWithoutTicketsConverter passengerConverter;

    @Autowired
    public PassengerServiceImpl(PassengerDAO passengerDAO,
                                DepartureDAO departureDAO, TripDAO tripDAO,
                                PassengerToPassengerWithoutTicketsConverter passengerConverter,
                                OccupiedSeatDAO occupiedSeatDAO, TicketDAO ticketDAO) {
        this.passengerDAO = passengerDAO;
        this.departureDAO = departureDAO;
        this.passengerConverter = passengerConverter;
        this.tripDAO = tripDAO;
        this.occupiedSeatDAO = occupiedSeatDAO;
        this.ticketDAO = ticketDAO;
    }

    @Transactional(readOnly = true)
    public List<PassengerWithoutTickets> findAllPassengers() {
        return passengerDAO.findAll().parallelStream()
                .map(passenger -> passengerConverter.convertTo(passenger))
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(Passenger passenger) {
        passengerDAO.save(passenger);
    }

    @Override
    @Transactional(readOnly = true)
    public Passenger findById(Integer id) {
        return passengerDAO.findById(id);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void buyTicket(String username, Integer tripId, Integer leftDepartureIndex,
                          Integer rightDepartureIndex, Integer coachNumber, Integer seatNumber) {
        IndexOutOfBoundsException boundsExc = new IndexOutOfBoundsException("Invalid departure indexes!");
        if (leftDepartureIndex <= 0 || leftDepartureIndex > rightDepartureIndex) {
            throw boundsExc;
        }
        Trip trip = tripDAO.findById(tripId);
        notNullElseThrowException(trip, new NoSuchEntityException("There is no such trip!", Trip.class));
        List<Departure> departures = departureDAO.findByTripIdAndNumberInTripBetween(trip.getId(),
                leftDepartureIndex, rightDepartureIndex, false, true);
        if (departures.size() == 0) {
            throw boundsExc;
        }
        Passenger passenger = passengerDAO.findByUsername(username);
        notNullElseThrowException(passenger, new NoSuchEntityException("There is no such passenger", Passenger.class));

        Ticket newTicket = new Ticket().setTrip(trip).setPassenger(passenger);
        List<OccupiedSeat> occupiedSeats = new LinkedList<>();
        newTicket.setOccupiedSeats(occupiedSeats);
        newTicket.setFrom(departures.get(0)).setTo(departures.get(departures.size() - 1));
        ticketDAO.save(newTicket);
        for (Departure departure : departures) {
            OccupiedSeat occupiedSeat = occupiedSeatDAO.findByDepartureAndSeatNumAndCoachNum(departure, seatNumber, coachNumber);
            if (occupiedSeat != null) {
                throw new TicketAlreadyBoughtException("This is already engaged!");
            }

            if (LocalDateTime.now().plusMinutes(10).isAfter(departure.getDateTimeFrom())) {
                throw new TooLateForBuyingTicketException("You must buy a seat earlier than 10 minutes before departure!");
            }

            if (departure.getFreeSitsCount() <= 0) {
                throw new NoSiteOnDepartureException("There is no free sits on this departure!");
            }
            occupiedSeat = new OccupiedSeat();
            occupiedSeat.setTicket(newTicket);
            occupiedSeat.setSeat(new SeatId(seatNumber, coachNumber, departure));
            occupiedSeats.add(occupiedSeat);

            departure.decrementFreeSeatsCount();
        }
        ticketDAO.save(newTicket);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PassengerWithoutTickets> findAllPassengersByDepartureId(Integer departureId) {
        return passengerDAO.findAllPassengersByDepartureId(departureId).stream()
                .map(passenger -> passengerConverter.convertTo(passenger))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PassengerWithoutTickets> findAllPassengersByTripId(Integer tripId) {
        return passengerDAO.findAllPassengersByTripId(tripId).stream()
                .map(passenger -> passengerConverter.convertTo(passenger))
                .collect(Collectors.toList());
    }

    @Override
    public List<PassengerWithoutTickets> findAllPassengersByTripIdAndDepartureIndexBounds(Integer tripId, Integer from, Integer to) {
        return passengerDAO.findAllPassengersByTripIdAndDepartureIndexBounds(tripId, from, to).stream()
                .map(passenger -> passengerConverter.convertTo(passenger))
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketDTO> getPassengerTickets(String username) {
        Passenger passenger = passengerDAO.findByUsername(username);
        if (passenger == null) {
            throw new IllegalArgumentException("Invalid user");
        }
        List<Ticket> passengerTickets = ticketDAO.findByPassengerAndAfter(passenger, LocalDateTime.now().plusMinutes(10));
        List<TicketDTO> tickets = new LinkedList<>();
        for (Ticket ticket : passengerTickets) {
            SeatId seat = ticket.getOccupiedSeats().get(0).getSeat();
            tickets.add(new TicketDTO(ticket.getFrom().getStationFrom().getTitle(), ticket.getTo().getStationTo().getTitle(),
                    ticket.getFrom().getDateTimeFrom().toDateTime().getMillis(),
                    ticket.getTo().getDateTimeTo().toDateTime().getMillis(),
                    seat.getCoachNumber(), seat.getSeatNumber()));
        }
        return tickets;
    }

    @Override
    public Boolean isRegistered(Integer tripId, String username) {
        Passenger passenger = passengerDAO.findByUsername(username);
        if (passenger == null) {
            throw new IllegalArgumentException("Invalid user");
        }
        return ticketDAO.findByTripIdAndPassenger(tripId, passenger) != null;
    }

    private void notNullElseThrowException(Object obj, RuntimeException exc) {
        if (obj == null) throw exc;
    }
}
