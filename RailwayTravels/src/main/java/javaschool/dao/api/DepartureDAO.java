package javaschool.dao.api;

import java.util.List;
import javaschool.entity.Departure;
import javaschool.entity.Station;
import org.joda.time.LocalDateTime;

public interface DepartureDAO extends GenericDAO<Departure, Integer> {
    Departure findById(Integer id, boolean fetchStations);
    List<Departure> findAll(boolean fetchStations, boolean orderByDateTimeFrom);
    List<Departure> findByTripIdAndNumberInTripBetween(Integer tripId, Integer leftBound, Integer rightBound,
                                            boolean fetchStations, boolean orderByDateTimeFrom);
}
