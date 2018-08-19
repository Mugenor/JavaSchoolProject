import java.util.LinkedList;
import java.util.List;
import javaschool.dao.api.DepartureDAO;
import javaschool.dao.api.OccupiedSeatDAO;
import javaschool.dao.api.PassengerDAO;
import javaschool.dao.api.TicketDAO;
import javaschool.dao.api.TripDAO;
import javaschool.entity.Departure;
import javaschool.entity.OccupiedSeat;
import javaschool.entity.Passenger;
import javaschool.entity.Station;
import javaschool.entity.Ticket;
import javaschool.entity.Trip;
import javaschool.service.exception.TicketAlreadyBoughtException;
import javaschool.service.exception.TooLateForBuyingTicketException;
import javaschool.service.impl.PassengerServiceImpl;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@RunWith(MockitoJUnitRunner.class)
public class BuyTicketTest {
    private final static int TRIP_ID = 1;
    private final static String USERNAME = "USERNAME";
    private final static String TITLE_1 = "TITLE_1";
    private final static String TITLE_2 = "TITLE_2";
    private final static String TITLE_3 = "TITLE_3";
    @InjectMocks
    private PassengerServiceImpl passengerService;
    @Mock
    private TripDAO tripDAO;
    @Mock
    private DepartureDAO departureDAO;
    @Mock
    private OccupiedSeatDAO occupiedSeatDAO;
    @Mock
    private PassengerDAO passengerDAO;
    @Mock
    private TicketDAO ticketDAO;

    @Before
    public void init() {
        when(passengerDAO.findByUsername(USERNAME)).thenReturn(new Passenger());
    }

    @Test
    public void successfulBuyTicket() {
        Trip trip = generateTrip(LocalDateTime.now().plusHours(5));
        when(tripDAO.findById(TRIP_ID)).thenReturn(trip);
        when(departureDAO.findByTripIdAndNumberInTripBetween(TRIP_ID,
                1, 2, false, true)).thenReturn(trip.getDepartures());
        passengerService.buyTicket(USERNAME, TRIP_ID, 1, 2, 1, 1);
        verify(tripDAO).findById(TRIP_ID);
        verify(passengerDAO, times(2)).findByUsername(USERNAME);
        verify(departureDAO).findByTripIdAndNumberInTripBetween(TRIP_ID,
                1, 2, false, true);
        verify(occupiedSeatDAO, times(2))
                .findByDepartureAndSeatNumAndCoachNum(any(), eq(1), eq(1));
        verify(ticketDAO, times(1))
                .save(any());
    }

    @Test(expected = TooLateForBuyingTicketException.class)
    public void buyTicket_TooLate() {
        Trip trip = generateTrip(LocalDateTime.now().plusMinutes(4));
        when(tripDAO.findById(TRIP_ID)).thenReturn(trip);
        when(departureDAO.findByTripIdAndNumberInTripBetween(TRIP_ID,
                1, 2, false, true)).thenReturn(trip.getDepartures());
        passengerService.buyTicket(USERNAME, TRIP_ID, 1, 2, 1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buyTicket_invalidDepartureIndexBounds() {
        passengerService.buyTicket(USERNAME, 1, -1, 1000,
                1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buyTicket_passengerAlreadyRegistered() {
        Trip trip = generateTrip(LocalDateTime.now().plusHours(5));
        when(tripDAO.findById(TRIP_ID)).thenReturn(trip);
        when(departureDAO.findByTripIdAndNumberInTripBetween(TRIP_ID,
                1, 2, false, true)).thenReturn(trip.getDepartures());
        when(ticketDAO.findByTripIdAndPassenger(TRIP_ID, new Passenger()))
                .thenReturn(new Ticket());
        passengerService.buyTicket(USERNAME, TRIP_ID, 1, 2, 1, 1);
    }

    @Test(expected = TicketAlreadyBoughtException.class)
    public void buyTicket_alreadyBought() {
        Trip trip = generateTrip(LocalDateTime.now().plusHours(5));
        when(tripDAO.findById(TRIP_ID)).thenReturn(trip);
        when(departureDAO.findByTripIdAndNumberInTripBetween(TRIP_ID,
                1, 2, false, true)).thenReturn(trip.getDepartures());
        when(occupiedSeatDAO.findByDepartureAndSeatNumAndCoachNum(any(Departure.class), eq(1), eq(1)))
                .thenReturn(new OccupiedSeat());
        passengerService.buyTicket(USERNAME, TRIP_ID, 1, 2, 1, 1);
    }

    private Trip generateTrip(LocalDateTime timeOffset) {
        List<Departure> departures = new LinkedList<>();
        Trip trip = new Trip();
        trip.setId(1).setDepartures(departures);
        departures.add(generateDeparture(timeOffset, 1, 10));
        departures.add(generateDeparture(timeOffset.plusHours(2), 2, 10));
        return trip;
    }

    private Departure generateDeparture(LocalDateTime timeFrom, int numberInTrip, int freeSeatsCount) {
        return new Departure().setDateTimeFrom(timeFrom)
                .setNumberInTrip(numberInTrip)
                .setFreeSitsCount(freeSeatsCount);
    }

    private Station generateStation(String title) {
        return new Station(title);
    }


}
