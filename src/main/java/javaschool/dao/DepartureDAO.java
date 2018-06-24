package javaschool.dao;

import javaschool.entity.Departure;
import org.joda.time.LocalDateTime;

import java.util.List;

public interface DepartureDAO extends GenericDAO<Departure, Integer> {
    List<Departure> findFromToBetween(String stFrom, String stTo, LocalDateTime dateFrom, LocalDateTime dateTo);
}
