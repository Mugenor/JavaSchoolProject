package javaschool.service.api;

import java.util.List;
import javaschool.entity.Departure;
import org.joda.time.LocalDateTime;

public interface DepartureService {
    Departure findById(Integer id);
    List<Departure> findAll(boolean fetchStations, boolean fetchTickets);
    List<Departure> findFromToBetween(String stFrom, String stTo, LocalDateTime dateFrom, LocalDateTime dateTo);
    void save(int sitsCount, String stationFrom, String stationTo, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo);
}
