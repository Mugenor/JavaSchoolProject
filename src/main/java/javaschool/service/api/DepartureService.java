package javaschool.service.api;

import java.util.List;
import javaschool.controller.dtoentity.DepartureDTO;
import javaschool.entity.Departure;
import org.joda.time.LocalDateTime;

public interface DepartureService {
    DepartureDTO findById(Integer id, boolean fetchStations, boolean fetchTickets);
    Departure findByIdRaw(Integer id, boolean fetchStations, boolean fetchTickets);
    List<DepartureDTO> findAll(boolean fetchStations, boolean fetchTickets);
    List<DepartureDTO> findFromToBetween(String stFrom, String stTo, LocalDateTime dateFrom, LocalDateTime dateTo);
    List<DepartureDTO> findFromToBetween(String stFrom, String stTo, String dateFrom, String dateTo);
    List<DepartureDTO> findByStationTitle(String stationTitle, boolean fetchStations, boolean fetchTickets);
    void save(int sitsCount, String stationFrom, String stationTo, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo);
}
