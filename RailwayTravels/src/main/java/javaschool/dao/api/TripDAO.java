package javaschool.dao.api;

import java.util.List;
import javaschool.entity.Departure;
import javaschool.entity.Trip;
import org.joda.time.LocalDateTime;

public interface TripDAO extends GenericDAO<Trip, Integer> {
    List<Trip> findAllAfter(LocalDateTime dateTime);
    List<Trip> findDateTimeFromBetweenBetweenOrDateTimeToBetween(LocalDateTime begin, LocalDateTime end);
    List<Trip> findByDepartures(List<Departure> departures);
    List<Trip> findByStationTitleToDateTimeToBetween(String stationTitleTo,
                                                         LocalDateTime dateTimeLeftBound, LocalDateTime dateTimeRightBound);
    List<Trip> findByStationTitleFromDateTimeFromBetween(String stationTitleFrom,
                                                     LocalDateTime dateTimeLeftBound, LocalDateTime dateTimeRightBound);
}
