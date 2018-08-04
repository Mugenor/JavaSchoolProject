package javaschool.service.impl;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javaschool.controller.dtoentity.PassengerWithoutTickets;
import javaschool.dao.api.DepartureDAO;
import javaschool.dao.api.OccupiedSeatDAO;
import javaschool.dao.api.PassengerDAO;
import javaschool.dao.api.SeatDAO;
import javaschool.dao.api.TicketDAO;
import javaschool.dao.api.TripDAO;
import javaschool.entity.Departure;
import javaschool.entity.OccupiedSeat;
import javaschool.entity.Passenger;
import javaschool.entity.Seat;
import javaschool.entity.Ticket;
import javaschool.entity.Trip;
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
    private SeatDAO seatDAO;
    private DepartureDAO departureDAO;
    private TripDAO tripDAO;
    private OccupiedSeatDAO occupiedSeatDAO;
    private TicketDAO ticketDAO;
    private PassengerToPassengerWithoutTicketsConverter passengerConverter;

    @Autowired
    public PassengerServiceImpl(PassengerDAO passengerDAO, SeatDAO seatDAO,
                                DepartureDAO departureDAO, TripDAO tripDAO,
                                PassengerToPassengerWithoutTicketsConverter passengerConverter,
                                OccupiedSeatDAO occupiedSeatDAO, TicketDAO ticketDAO) {
        this.passengerDAO = passengerDAO;
        this.seatDAO = seatDAO;
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
        if(leftDepartureIndex <= 0 || leftDepartureIndex > rightDepartureIndex) {
            throw boundsExc;
        }
        Trip trip = tripDAO.findById(tripId);
        notNullElseThrowException(trip, new NoSuchEntityException("There is no such trip!", Trip.class));
        List<Departure> departures = departureDAO.findByTripIdAndNumberInTripBetween(trip.getId(),
                leftDepartureIndex, rightDepartureIndex, false, false, true);
        if(departures.size() == 0) {
            throw boundsExc;
        }
        Passenger passenger = passengerDAO.findByUsername(username);
        notNullElseThrowException(passenger, new NoSuchEntityException("There is no such passenger", Passenger.class));

        Ticket newTicket = new Ticket().setTrip(trip).setPassenger(passenger);
        Set<OccupiedSeat> occupiedSeats = new TreeSet<>();
        newTicket.setOccupiedSeats(occupiedSeats);
        for (Departure departure : departures) {
            Seat seat = seatDAO.findSeatByDepartureAndCoachNumAndSeatNum(departure.getId(), coachNumber, seatNumber);
            notNullElseThrowException(seat, new NoSuchEntityException("There is no such seat", Seat.class));

            if (seat.getOccupiedSeat() != null) {
                throw new TicketAlreadyBoughtException("This seat was bought by someone else!");
            }

            if (LocalDateTime.now().plusMinutes(10).isAfter(departure.getDateTimeFrom())) {
                throw new TooLateForBuyingTicketException("You must buy a seat earlier than 10 minutes before departure!");
            }

            if (departure.getFreeSitsCount() <= 0) {
                throw new NoSiteOnDepartureException("There is no free sits on this departure!");
            }
            OccupiedSeat occupiedSeat = new OccupiedSeat().setSeat(seat).setTicket(newTicket);
            seat.setOccupiedSeat(occupiedSeat);
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

    private void notNullElseThrowException(Object obj, RuntimeException exc) {
        if (obj == null) throw exc;
    }
}
