package javaschool.service;

import javaschool.entity.Departure;
import javaschool.entity.Station;
import org.joda.time.LocalDateTime;

import java.util.List;

public interface DepartureService {
    List<Departure> findAll();
    List<Departure> findFromToBetween(String stFrom, String stTo, LocalDateTime dateFrom, LocalDateTime dateTo);
    void save(Departure departure);
}
