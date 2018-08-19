import java.util.LinkedList;
import java.util.List;
import javaschool.controller.dtoentity.TripDTO;
import javaschool.dao.api.TripDAO;
import javaschool.entity.Departure;
import javaschool.entity.Station;
import javaschool.entity.Trip;
import javaschool.service.converter.DepartureToDepartureDTOConverter;
import javaschool.service.converter.TripToTripDTOConverter;
import javaschool.service.impl.SearchTripsWithTransfersImpl;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class FindWayWithTransfersTest {
    private static final String[] TITLES = {"TITLE_1", "TITLE_2", "TITLE_3", "TITLE_4", "TITLE_5", "TITLE_6"};
    @InjectMocks
    private SearchTripsWithTransfersImpl searchTripsWithTransfers;
    @Mock
    private TripDAO tripDAO;
    private TripToTripDTOConverter tripToTripDTOConverter;

    @Before
    public void init() {
        tripToTripDTOConverter = spy(new TripToTripDTOConverter(
                new DepartureToDepartureDTOConverter()
        ));
        initMocks(this);
    }

    @Test
    public void successSearchTest() {
        List<Trip> emptyList = new LinkedList<>();
        when(tripDAO.findByStationTitleFromDateTimeFromBetween(
                anyString(), any(LocalDateTime.class), any(LocalDateTime.class)
        )).thenReturn(emptyList);

        when(tripDAO.findByStationTitleFromDateTimeFromBetween(
                eq(TITLES[0]), any(LocalDateTime.class), any(LocalDateTime.class)
        )).thenReturn(generateListWithOneTrip(1, LocalDateTime.now().plusHours(1), TITLES[0], TITLES[1], TITLES[2], TITLES[4]));

        when(tripDAO.findByStationTitleFromDateTimeFromBetween(
                eq(TITLES[1]), any(LocalDateTime.class), any(LocalDateTime.class)
        )).thenReturn(generateListWithOneTrip(2, LocalDateTime.now().plusHours(2).plusMinutes(30),
                TITLES[1], TITLES[3], TITLES[4]));

        List<List<TripDTO>> fromToBetweenWithTransfers = searchTripsWithTransfers
                .findFromToBetweenWithTransfers(TITLES[0], TITLES[4],
                        LocalDateTime.now(), LocalDateTime.now().plusDays(1), 1);
        verify(tripDAO, times(3)).findByStationTitleFromDateTimeFromBetween(anyString(), any(LocalDateTime.class), any(LocalDateTime.class));
        verify(tripToTripDTOConverter, times(2)).convertTo(any(Trip.class));
        List<List<TripDTO>> resultList = new LinkedList<>();
        List<TripDTO> path = new LinkedList<>();
        path.add(new TripDTO().setId(1));
        path.add(new TripDTO().setId(2));
        resultList.add(path);
        path = new LinkedList<>();
        path.add(new TripDTO().setId(1));
        resultList.add(path);
        assertEquals(resultList, fromToBetweenWithTransfers);
    }

    @Test
    public void failureSearchTest() {
        List<Trip> emptyList = new LinkedList<>();
        when(tripDAO.findByStationTitleFromDateTimeFromBetween(
                anyString(), any(LocalDateTime.class), any(LocalDateTime.class)
        )).thenReturn(emptyList);

        when(tripDAO.findByStationTitleFromDateTimeFromBetween(
                eq(TITLES[0]), any(LocalDateTime.class), any(LocalDateTime.class)
        )).thenReturn(generateListWithOneTrip(1, LocalDateTime.now().plusHours(1), TITLES[0], TITLES[1], TITLES[2], TITLES[4]));

        when(tripDAO.findByStationTitleFromDateTimeFromBetween(
                eq(TITLES[1]), any(LocalDateTime.class), any(LocalDateTime.class)
        )).thenReturn(generateListWithOneTrip(2, LocalDateTime.now().plusHours(2).plusMinutes(30),
                TITLES[1], TITLES[3], TITLES[4]));

        List<List<TripDTO>> fromToBetweenWithTransfers = searchTripsWithTransfers
                .findFromToBetweenWithTransfers(TITLES[0], TITLES[4],
                        LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), 1);
        verify(tripDAO, times(1)).findByStationTitleFromDateTimeFromBetween(anyString(), any(LocalDateTime.class), any(LocalDateTime.class));
        verify(tripToTripDTOConverter, times(1)).convertTo(any(Trip.class));
        assertEquals(new LinkedList<List<TripDTO>>(), fromToBetweenWithTransfers);
    }


    private List<Trip> generateListWithOneTrip(int id, LocalDateTime timeOffset, String... stationTitles) {
        List<Departure> departures = new LinkedList<>();
        Trip trip = new Trip().setId(id).setDepartures(departures);
        if (stationTitles.length < 2) {
            throw new IllegalArgumentException();
        }
        departures.add(new Departure()
                .setStationFrom(new Station(stationTitles[0]))
                .setStationTo(new Station(stationTitles[1]))
                .setDateTimeFrom(timeOffset)
                .setDateTimeTo(timeOffset.plusHours(1))
                .setSeatInCoach(36)
                .setCoachCount(1)
                .setFreeSitsCount(36)
                .setNumberInTrip(1));

        for (int i = 2; i < stationTitles.length; ++i) {
            departures.add(new Departure()
                    .setStationFrom(new Station(stationTitles[i - 1]))
                    .setStationTo(new Station(stationTitles[i]))
                    .setDateTimeFrom(timeOffset.plusHours(i))
                    .setDateTimeTo(timeOffset.plusHours(i + 1))
                    .setSeatInCoach(36)
                    .setCoachCount(1)
                    .setFreeSitsCount(36)
                    .setNumberInTrip(i - 1));
        }
        List<Trip> trips = new LinkedList<>();
        trips.add(trip);
        return trips;
    }
}
