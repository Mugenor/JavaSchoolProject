import java.util.LinkedHashSet;
import java.util.LinkedList;
import javaschool.dao.api.DepartureDAO;
import javaschool.dao.api.StationDAO;
import javaschool.entity.Coach;
import javaschool.entity.Departure;
import javaschool.entity.Station;
import javaschool.entity.Ticket;
import javaschool.service.api.DepartureService;
import javaschool.service.converter.DepartureToDepartureDTOConverter;
import javaschool.service.converter.StringToLocalDateTimeConverter;
import javaschool.service.impl.DepartureServiceImpl;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent .class)
public class DepartureServiceTest {
    private static final String STATION_TITLE_FROM = "stFrom";
    private static final String STATION_TITLE_TO = "stTo";
    private static final Station STATION_FROM = new Station(STATION_TITLE_FROM);
    private static final Station STATION_TO = new Station(STATION_TITLE_TO);
    private static final LocalDateTime DATE_TIME_FROM = LocalDateTime.now().plusHours(1);
    private static final LocalDateTime DATE_TIME_TO = DATE_TIME_FROM.plusDays(1);
    private static final int coachCount = 2;
    private DepartureService departureService;
    private StringToLocalDateTimeConverter stringToLocalDateTimeConverter = new StringToLocalDateTimeConverter();
    private DepartureToDepartureDTOConverter departureToDepartureDTOConverter =
            new DepartureToDepartureDTOConverter(stringToLocalDateTimeConverter);
    @Mock
    private DepartureDAO departureDAO;
    @Mock
    private StationDAO stationDAO;

    @Before
    public void init() {
        departureService = new DepartureServiceImpl(departureDAO, stationDAO, stringToLocalDateTimeConverter, departureToDepartureDTOConverter);
        STATION_FROM.setDepartures(new LinkedList<>());
        STATION_TO.setDepartures(new LinkedList<>());
        when(stationDAO.findByTitle(STATION_TITLE_FROM)).thenReturn(STATION_FROM);
        when(stationDAO.findByTitle(STATION_TITLE_TO)).thenReturn(STATION_TO);
    }

    @Test
    public void saveDeparture() {
        Departure departure = getDeparture();
        assertEquals(departure, departureService.save(coachCount, STATION_TITLE_FROM, STATION_TITLE_TO, DATE_TIME_FROM, DATE_TIME_TO));
        verify(stationDAO).findByTitle(STATION_TITLE_FROM);
        verify(stationDAO).findByTitle(STATION_TITLE_TO);
        verify(departureDAO).save(departure);
    }

    private Departure getDeparture() {
        Departure departure = new Departure();
        departure.setId(1);
        departure.setFreeSitsCount(coachCount * Coach.DEFAULT_SEATS_NUM);
        departure.setSitsCount(coachCount * Coach.DEFAULT_SEATS_NUM);
        departure.setDateTimeFrom(DATE_TIME_FROM);
        departure.setDateTimeTo(DATE_TIME_TO);
        departure.setStationTo(STATION_TO);
        departure.setStationFrom(STATION_FROM);
        LinkedHashSet<Coach> coaches = new LinkedHashSet<>();
        departure.setCoaches(coaches);
        for(int i = 1; i <= coachCount; ++i) {
            Coach coach = new Coach();
            LinkedHashSet<Ticket> tickets = new LinkedHashSet<>();
            coach.setTickets(tickets);
            coach.setCoachNumber(i);
            coach.setDeparture(departure);
            coaches.add(coach);
            for(int j = 1; j <= Coach.DEFAULT_SEATS_NUM; ++j) {
                Ticket ticket = new Ticket();
                ticket.setSiteNum(j);
                ticket.setCoach(coach);
                tickets.add(ticket);
            }
        }
        return departure;
    }

}
