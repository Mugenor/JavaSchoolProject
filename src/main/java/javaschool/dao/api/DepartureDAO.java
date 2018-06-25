package javaschool.dao.api;

import javaschool.entity.Departure;
import javaschool.entity.Station;
import org.joda.time.LocalDateTime;

import java.util.List;

public interface DepartureDAO extends GenericDAO<Departure, Integer> {
    List<Departure> findFromToBetween(Station stFrom, Station stTo, LocalDateTime dateFrom, LocalDateTime dateTo);
}
