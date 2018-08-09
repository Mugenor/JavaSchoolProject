package javaschool.dao.api;

import java.util.List;
import javaschool.entity.Departure;
import javaschool.entity.Passenger;
import javaschool.entity.Trip;
import org.joda.time.LocalDateTime;

public interface TripDAO extends GenericDAO<Trip, Integer> {
    List<Trip> findAllAfter(LocalDateTime dateTime);
    List<Trip> findDateTimeFromBetweenBetweenOrDateTimeToBetween(LocalDateTime begin, LocalDateTime end);
    List<Trip> findByStationFromTitle(String stationFromTitle);
    List<Trip> findByStationTitleToDateTimeToBetween(String stationTitleTo,
                                                         LocalDateTime dateTimeLeftBound, LocalDateTime dateTimeRightBound);
    List<Trip> findByStationTitleFromDateTimeFromBetween(String stationTitleFrom,
                                                     LocalDateTime dateTimeLeftBound, LocalDateTime dateTimeRightBound);
}
