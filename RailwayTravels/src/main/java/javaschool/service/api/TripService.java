package javaschool.service.api;

import java.util.List;
import javaschool.controller.dtoentity.CoachDTO;
import javaschool.controller.dtoentity.TrainInfo;
import javaschool.controller.dtoentity.NewDepartureDTO;
import javaschool.controller.dtoentity.TripDTO;
import javaschool.controller.dtoentity.TripInfo;
import org.joda.time.LocalDateTime;

public interface TripService {
    List<TripDTO> findAll();
    List<TripDTO> findFromToBetween(String stFrom, String stTo, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo);
    List<TripDTO> findAllToday();
    List<TripDTO> findAllAvailable();
    List<TripDTO> findByStationTitle(String stationTitle);
    List<CoachDTO> findOccupiedCoachesWithSeatsByTripIdAndDepartureBounds(Integer tripId, Integer departureFromIndex, Integer departureToIndex);
    TrainInfo getDepartureInfoByTripIdAndDepartureBounds(Integer tripId, Integer departureFromIndex, Integer departureToIndex);
    TripDTO save(List<NewDepartureDTO> departures);
    TripDTO saveWithNotification(List<NewDepartureDTO> departures);
    TripInfo getTripInfo(Integer tripId, Integer departureFromIndex, Integer departureToIndex);
}
