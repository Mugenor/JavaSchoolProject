package javaschool.dao.api;

import java.util.List;
import javaschool.entity.Departure;
import javaschool.entity.Station;
import org.joda.time.LocalDateTime;

public interface DepartureDAO extends GenericDAO<Departure, Integer> {
    Departure findById(Integer id, boolean fetchStations, boolean fetchTickets);
    List<Departure> findAll(boolean fetchStations, boolean fetchTickets);
    List<Departure> findFromToBetween(Station stFrom, Station stTo, LocalDateTime dateFrom, LocalDateTime dateTo);
    Departure findByStFromAndStToAndDateFromAndDateTo(Station stFrom, Station stTo, LocalDateTime dateFrom, LocalDateTime dateTo);
    List<Departure> findByStationTitleFrom(String stationTitle, boolean fetchStations, boolean fetchTickets);
}
