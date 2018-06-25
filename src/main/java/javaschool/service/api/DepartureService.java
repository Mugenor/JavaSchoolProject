package javaschool.service.api;

import javaschool.entity.Departure;
import org.joda.time.LocalDateTime;

import java.util.List;

public interface DepartureService {
    List<Departure> findAll();
    List<Departure> findFromToBetween(String stFrom, String stTo, LocalDateTime dateFrom, LocalDateTime dateTo);
    void save(Departure departure);
}
