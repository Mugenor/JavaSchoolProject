import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import javaschool.dao.api.DepartureDAO;
import javaschool.dao.api.PassengerDAO;
import javaschool.dao.api.PassengerWithTicketDAO;
import javaschool.dao.api.TicketDAO;
import javaschool.entity.Coach;
import javaschool.entity.Departure;
import javaschool.entity.Passenger;
import javaschool.entity.Station;
import javaschool.entity.Ticket;
import javaschool.entity.User;
import javaschool.service.api.PassengerService;
import javaschool.service.converter.PassengerToPassengerWithoutTicketsConverter;
import javaschool.service.exception.NoSiteOnDepartureException;
import javaschool.service.exception.PassengerRegisteredException;
import javaschool.service.exception.TicketAlreadyBoughtException;
import javaschool.service.exception.TooLateForBuyingTicketException;
import javaschool.service.impl.PassengerServiceImpl;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;


import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PassengerServiceTest {
    private static final String DEFAULT_USERNAME = "username";
    private static final String NOT_DEFAULT_USERNAME = "not_username";
    private static final String DEFAULT_NAME = "name";
    private static final String NOT_DEFAULT_NAME = "not_name";
    private static final String DEFAULT_SURNAME = "surname";
    private static final String NOT_DEFAULT_SURNAME = "not_surname";
    private static final LocalDate DEFAULT_BIRTHDAY = LocalDate.parse("2000-01-01");
    private static final LocalDate NOT_DEFAULT_BIRTHDAY = DEFAULT_BIRTHDAY.plusYears(1);
    private static final int DEPARTURE_ID = 1;
    private static final int COACH_NUM = 1;
    private static final int SEAT_NUM = 1;
    private static final Station STATION_FROM = new Station("stFrom");
    private static final Station STATION_TO = new Station("stTo");
    private static final LocalDateTime TOO_LATE_DATE_TIME_FROM = LocalDateTime.now().plusMinutes(5);
    private static final LocalDateTime DATE_TIME_FROM = LocalDateTime.now().plusHours(1);
    private static final LocalDateTime DATE_TIME_TO = LocalDateTime.now().plusHours(2);
    private static final String THIRD_DEFAULT_NAME = "third_name";
    private static final String THIRD_DEFAULT_SURNAME = "third_surname";
    private static final LocalDate THIRD_DEFAULT_BIRTHDAY = NOT_DEFAULT_BIRTHDAY.plusYears(1);
    private static final String THIRD_DEFAULT_USERNAME = "third_default_username";
    private PassengerService passengerService;
    @Mock
    private PassengerDAO passengerDAO;
    @Mock
    private TicketDAO ticketDAO;
    @Mock
    private DepartureDAO departureDAO;
    @Mock
    private PassengerWithTicketDAO passengerWithTicketDAO;
    @Mock
    private PassengerToPassengerWithoutTicketsConverter passengerConverter;

    @Before
    public void init() {
        passengerService = new PassengerServiceImpl(passengerDAO, ticketDAO,
                departureDAO, passengerWithTicketDAO, passengerConverter);
        ((PassengerServiceImpl) passengerService).setSelfProxy(passengerService);
        Departure defaultDeparture = getInitializedDeparture(DEPARTURE_ID, STATION_FROM, STATION_TO, DATE_TIME_FROM, DATE_TIME_TO);
        Ticket defaultTicket = getDefaultTicket(SEAT_NUM);
        Passenger defaultPassenger = getDefaultPassenger(DEFAULT_NAME, DEFAULT_SURNAME, DEFAULT_BIRTHDAY);
        Ticket ticketWithPassenger = getDefaultTicket(SEAT_NUM + 2);
        ticketWithPassenger.setPassenger(defaultPassenger);
        Departure departureWithoutFreeSeats = getInitializedDeparture(DEPARTURE_ID + 2, STATION_FROM, STATION_TO, DATE_TIME_FROM, DATE_TIME_TO);
        departureWithoutFreeSeats.setFreeSitsCount(0);
        when(departureDAO.findById(DEPARTURE_ID)).thenReturn(defaultDeparture);
        when(departureDAO.findById(DEPARTURE_ID + 1)).thenReturn(
                getInitializedDeparture(DEPARTURE_ID + 1, STATION_FROM, STATION_TO,
                LocalDateTime.now().plusMinutes(5), LocalDateTime.now().plusDays(2)));
        when(departureDAO.findById(DEPARTURE_ID + 2)).thenReturn(departureWithoutFreeSeats);
        when(ticketDAO.findTicketByDepartureAndCoachNumAndSeatNum(DEPARTURE_ID, COACH_NUM, SEAT_NUM))
                .thenReturn(defaultTicket);
        when(ticketDAO.findTicketByDepartureAndCoachNumAndSeatNum(DEPARTURE_ID + 1, COACH_NUM, SEAT_NUM))
                .thenReturn(defaultTicket);
        when(ticketDAO.findTicketByDepartureAndCoachNumAndSeatNum(DEPARTURE_ID + 2, COACH_NUM, SEAT_NUM))
                .thenReturn(defaultTicket);
        when(ticketDAO.findTicketByDepartureAndCoachNumAndSeatNum(DEPARTURE_ID, COACH_NUM, SEAT_NUM + 1))
                .thenReturn(getDefaultTicket(SEAT_NUM + 1));
        when(ticketDAO.findTicketByDepartureAndCoachNumAndSeatNum(DEPARTURE_ID, COACH_NUM, SEAT_NUM + 2))
                .thenReturn(ticketWithPassenger);
        when(passengerDAO.findByUsername(DEFAULT_USERNAME)).thenReturn(defaultPassenger);
        when(passengerDAO.findByUsername(NOT_DEFAULT_USERNAME))
                .thenReturn(getDefaultPassenger(NOT_DEFAULT_NAME, NOT_DEFAULT_SURNAME, NOT_DEFAULT_BIRTHDAY));
        when(passengerDAO.findByUsername(THIRD_DEFAULT_USERNAME))
                .thenReturn(getDefaultPassenger(THIRD_DEFAULT_NAME, THIRD_DEFAULT_SURNAME, THIRD_DEFAULT_BIRTHDAY));
        doThrow(new DataIntegrityViolationException("exc")).when(passengerWithTicketDAO)
                .save(defaultPassenger, getDefaultTicket(SEAT_NUM + 1), defaultDeparture);
        doThrow(new DataIntegrityViolationException("exc")).when(passengerWithTicketDAO)
                .save(getDefaultPassenger(NOT_DEFAULT_NAME, NOT_DEFAULT_SURNAME, NOT_DEFAULT_BIRTHDAY)
                        , defaultTicket, defaultDeparture);
        doThrow(new DataIntegrityViolationException("exc")).when(passengerWithTicketDAO)
                .save(defaultPassenger, defaultTicket, defaultDeparture);
    }

    @Test
    public void buyNotDefaultTicketNotDefaultUser() {
        passengerService.buyTicket(NOT_DEFAULT_USERNAME, DEPARTURE_ID, COACH_NUM, SEAT_NUM + 1);
        verify(departureDAO).findById(DEPARTURE_ID);
        verify(passengerDAO).findByUsername(NOT_DEFAULT_USERNAME);
        verify(ticketDAO).findTicketByDepartureAndCoachNumAndSeatNum(DEPARTURE_ID, COACH_NUM, SEAT_NUM + 1);
        Departure departure = getInitializedDeparture(DEPARTURE_ID, STATION_FROM, STATION_TO, DATE_TIME_FROM, DATE_TIME_TO);
        departure.decrementFreeSeatsCount();
        verify(passengerWithTicketDAO).save(getDefaultPassenger(NOT_DEFAULT_NAME, NOT_DEFAULT_SURNAME, NOT_DEFAULT_BIRTHDAY),
                getDefaultTicket(SEAT_NUM + 1), departure);
    }

    @Test(expected = TicketAlreadyBoughtException.class)
    public void buyBookedTicket() {
        passengerService.buyTicket(THIRD_DEFAULT_USERNAME, DEPARTURE_ID, COACH_NUM, SEAT_NUM + 2);
    }



    @Test(expected = PassengerRegisteredException.class)
    public void buyDefaultTicketDefaultUser() {
        passengerService.buyTicket(DEFAULT_USERNAME, DEPARTURE_ID, COACH_NUM, SEAT_NUM);
        verify(departureDAO).findById(DEPARTURE_ID);
        verify(passengerDAO).findByUsername(DEFAULT_USERNAME);
        verify(ticketDAO).findTicketByDepartureAndCoachNumAndSeatNum(DEPARTURE_ID, COACH_NUM, SEAT_NUM);
        Passenger defaultPassenger = getDefaultPassenger(DEFAULT_NAME, DEFAULT_SURNAME, DEFAULT_BIRTHDAY);
        Ticket defaultTicket = getDefaultTicket(1);
        Departure departure = getInitializedDeparture(DEPARTURE_ID, STATION_FROM, STATION_TO, DATE_TIME_FROM, DATE_TIME_TO);
        departure.decrementFreeSeatsCount();
        defaultPassenger.getTickets().add(defaultTicket);
        verify(passengerWithTicketDAO).save(defaultPassenger, defaultTicket, departure);
    }

    @Test(expected = TooLateForBuyingTicketException.class)
    public void buyTicketWhenTooLate() {
        passengerService.buyTicket(DEFAULT_USERNAME, DEPARTURE_ID + 1, COACH_NUM, SEAT_NUM);
    }

    @Test(expected = NoSiteOnDepartureException.class)
    public void buyTicketWhenNoFreeSeats() {
        passengerService.buyTicket(DEFAULT_USERNAME, DEPARTURE_ID + 2, COACH_NUM, SEAT_NUM);
    }

    private Coach getInitializedCoach(int coachNumber) {
        Coach coach = new Coach();
        coach.setCoachNumber(coachNumber);
        Set<Ticket> tickets = new LinkedHashSet<>();
        for (int i = 0; i < Coach.DEFAULT_SEATS_NUM; ++i) {
            Ticket newTicket = new Ticket();
            newTicket.setCoach(coach);
            newTicket.setSiteNum(i + 1);
            tickets.add(newTicket);
        }
        coach.setTickets(tickets);
        return coach;
    }

    private Ticket getDefaultTicket(int id) {
        Ticket ticket = new Ticket();
        ticket.setSiteNum(id);
        ticket.setId(id);
        return ticket;
    }


    private Departure getInitializedDeparture(int id, Station stFrom, Station stTo,
                                              LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        Departure departure = new Departure();
        Set<Coach> coaches = new LinkedHashSet<>();
        coaches.add(getInitializedCoach(1));
        departure.setCoaches(coaches);
        departure.setId(id);
        departure.setStationFrom(stFrom);
        departure.setStationTo(stTo);
        departure.setDateTimeFrom(dateTimeFrom);
        departure.setDateTimeTo(dateTimeTo);
        departure.setSitsCount(Coach.DEFAULT_SEATS_NUM);
        departure.setFreeSitsCount(Coach.DEFAULT_SEATS_NUM);
        return departure;
    }

    private Passenger getDefaultPassenger(String name, String surname, LocalDate birthday) {
        Passenger passenger = new Passenger(name, surname, birthday);
        passenger.setTickets(new LinkedList<>());
        return passenger;
    }
}
