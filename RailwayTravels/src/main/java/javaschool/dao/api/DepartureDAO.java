package javaschool.dao.api;

import java.util.List;
import javaschool.entity.Departure;
import javaschool.entity.Station;
import javaschool.entity.Trip;
import org.joda.time.LocalDateTime;

public interface DepartureDAO extends GenericDAO<Departure, Integer> {
    Departure findById(Integer id, boolean fetchSeats, boolean fetchTickets);
    List<Departure> findAll(boolean fetchStations, boolean fetchSeats, boolean orderByDateTimeFrom);
    List<Departure> findAfterDateTime(LocalDateTime dateTime, boolean fetchStations, boolean fetchSeats, boolean orderByDateTimeFrom);
    List<Departure> findFromToBetween(Station stFrom, Station stTo, LocalDateTime dateFrom, LocalDateTime dateTo, boolean orderByDateTimeFrom);
    Departure findByStFromAndStToAndDateFromAndDateTo(Station stFrom, Station stTo, LocalDateTime dateFrom, LocalDateTime dateTo);
    List<Departure> findByStationTitleFrom(String stationTitle, boolean fetchStations, boolean fetchSeats, boolean orderByDateTimeFrom);
    List<Departure> findDateTimeFromBetweenOrDateTimeToBetween(LocalDateTime dateTimeFromLeft, LocalDateTime dateTimeFromRight,
                                                               LocalDateTime dateTimeToLeft, LocalDateTime dateTimeToRight,
                                                               boolean fetchStations, boolean fetchSeats, boolean orderByDateTimeFrom);
    List<Departure> findByTripIdAndNumberInTripBetween(Integer tripId, Integer leftBound, Integer rightBound,
                                            boolean fetchStations, boolean fetchSeats, boolean orderByDateTimeFrom);
}
