package javaschool.service.api;

import java.util.List;
import javaschool.controller.dtoentity.NewDepartureDTO;
import javaschool.controller.dtoentity.TripDTO;
import javaschool.entity.Trip;
import org.joda.time.LocalDateTime;

public interface TripService {
    List<TripDTO> findAll();
    List<TripDTO> findFromToBetween(String stFrom, String stTo, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo);
    List<TripDTO> findAllToday();
    List<TripDTO> findAllAvailable();
    List<TripDTO> findByStationTitle(String stationTitle);
    Trip save(List<NewDepartureDTO> departures);
}
