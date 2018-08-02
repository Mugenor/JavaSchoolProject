package javaschool.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import javaschool.controller.dtoentity.PassengerWithoutTickets;
import javaschool.dao.api.DepartureDAO;
import javaschool.dao.api.PassengerDAO;
import javaschool.dao.api.TicketDAO;
import javaschool.entity.Departure;
import javaschool.entity.Passenger;
import javaschool.entity.Seat;
import javaschool.service.api.PassengerService;
import javaschool.service.converter.PassengerToPassengerWithoutTicketsConverter;
import javaschool.service.exception.NoSiteOnDepartureException;
import javaschool.service.exception.NoSuchEntityException;
import javaschool.service.exception.PassengerRegisteredException;
import javaschool.service.exception.TicketAlreadyBoughtException;
import javaschool.service.exception.TooLateForBuyingTicketException;
import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PassengerServiceImpl implements PassengerService {
    private static final Logger log = Logger.getLogger(PassengerServiceImpl.class);

    private PassengerDAO passengerDAO;
    private TicketDAO ticketDAO;
    private DepartureDAO departureDAO;
    private PassengerToPassengerWithoutTicketsConverter passengerConverter;
    private PassengerService selfProxy;

    @Autowired
    public PassengerServiceImpl(PassengerDAO passengerDAO, TicketDAO ticketDAO,
                                DepartureDAO departureDAO,
                                PassengerToPassengerWithoutTicketsConverter passengerConverter) {
        this.passengerDAO = passengerDAO;
        this.ticketDAO = ticketDAO;
        this.departureDAO = departureDAO;
        this.passengerConverter = passengerConverter;
    }

    @Autowired
    public void setSelfProxy(PassengerService selfProxy) {
        this.selfProxy = selfProxy;
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
    @Transactional(readOnly = true)
    public void buyTicket(String username, Integer departureId, Integer coachNumber, Integer seatNumber) {
        try {
            selfProxy.buyTicketTransactional(username, departureId, coachNumber, seatNumber);
        } catch (DataIntegrityViolationException e) {
            Seat seat = ticketDAO.findTicketByDepartureAndCoachNumAndSeatNum(departureId, coachNumber, seatNumber);
            if(seat.getOccupiedSeat() == null) {
                log.info(username + " tried to buy few tickets on one departure!", e);
                throw new PassengerRegisteredException("You are already have a seat on this departure!", e);
            } else {
                throw new TicketAlreadyBoughtException("Sorry, but someone already got this seat!", e);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void buyTicketTransactional(String username, Integer departureId, Integer coachNumber, Integer seatNumber) {
        Departure departure = departureDAO.findById(departureId);
        Passenger passenger = passengerDAO.findByUsername(username);
        Seat seat = ticketDAO.findTicketByDepartureAndCoachNumAndSeatNum(departureId, coachNumber, seatNumber);

        notNullElseThrowException(passenger, new NoSuchEntityException("There is no such passenger", Passenger.class));
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

//        passengerWithTicketDAO.save(passenger, seat, departure);

//        seat.setPassenger(passenger);
//        passenger.getSeats().add(seat);
        departure.decrementFreeSeatsCount();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PassengerWithoutTickets> findAllPassengersByDepartureId(Integer departureId) {
        return passengerDAO.findAllPassengersByDepartureId(departureId).parallelStream()
                .map(passenger -> passengerConverter.convertTo(passenger))
                .collect(Collectors.toList());
    }


    private void notNullElseThrowException(Object obj, RuntimeException exc) {
        if (obj == null) throw exc;
    }
}
