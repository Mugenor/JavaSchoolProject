package javaschool.service.api;

import java.util.List;
import javaschool.controller.dtoentity.NewDepartureDTO;
import javaschool.controller.dtoentity.TrainInfo;
import javaschool.controller.dtoentity.TripDTO;
import javaschool.controller.dtoentity.TripInfo;
import org.joda.time.LocalDateTime;

/**
 * The interface Trip service.
 */
public interface TripService {
    /**
     * Finds all trips
     *
     * @return List of all trips converted to TripDTO.class
     */
    List<TripDTO> findAll();

    /**
     * Finds paths of trips from stFrom station to stTo station in specified time bounds without transfers
     *
     * @param stFrom       title of departure station
     * @param stTo         title of arrival station
     * @param dateTimeFrom left bound for departure time
     * @param dateTimeTo   right bound for arrival time
     * @return List of paths
     */
    List<TripDTO> findFromToBetween(String stFrom, String stTo, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo);

    /**
     * Find by id trip dto.
     *
     * @param tripId the trip id
     * @return the trip dto
     */
    TripDTO findById(Integer tripId);

    /**
     * Finds paths of trips from stFrom station to stTo station in specified time bounds and with specified
     * maximum transfer count
     *
     * @param stFrom            title of departure station
     * @param stTo              title of arrival station
     * @param dateTimeFrom      left bound for departure time
     * @param dateTimeTo        right bound for arrival time
     * @param maxTransfersCount maximum transfer count
     * @return List of paths. Path is an ordered list of trips with included departures.
     */
    List<List<TripDTO>> findFromToBetweenWithTransfers(String stFrom, String stTo,
                                                       LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo,
                                                       int maxTransfersCount);

    /**
     * Finds all trips which contains departures which arrives or departs today
     *
     * @return List of trips which arrives or departs today converted to TripDTO.class
     */
    List<TripDTO> findAllToday();

    /**
     * Finds all trips which is not departed yet
     *
     * @return List of trips which is not departed yet converted to TripDTO.class
     */
    List<TripDTO> findAllAvailable();

    /**
     * Finds all trips which is not departed yet from specified station
     *
     * @param stationTitle station with trips
     * @return List of trips which is not departed yet from specified station converted to TripDTO.class
     */
    List<TripDTO> findAvailableByStationTitle(String stationTitle);

    /**
     * Creates trainInfo for specified part of trip
     *
     * @param tripId             trip's id
     * @param departureFromIndex left bound for part of trip (departure's numberInTrip)
     * @param departureToIndex   right bound for part of trip (departure's numberInTrip)
     * @return departure info by trip id and departure bounds
     */
    TrainInfo getDepartureInfoByTripIdAndDepartureBounds(Integer tripId, Integer departureFromIndex, Integer departureToIndex);

    /**
     * Saves a trip by specified departures
     *
     * @param departures departures in trip
     * @return Created trip converted to TripDTO.class
     */
    TripDTO save(List<NewDepartureDTO> departures);

    /**
     * Creates tripInfo for specified part of trip
     *
     * @param tripId             trip's id
     * @param departureFromIndex left bound for part of trip (departure's numberInTrip)
     * @param departureToIndex   right bound for part of trip (departure's numberInTrip)
     * @return trip info
     */
    TripInfo getTripInfo(Integer tripId, Integer departureFromIndex, Integer departureToIndex);

    /**
     * Deletes trip if no passengers is registered on this trip
     *
     * @param tripId trip's id
     * @return deleted trip converted to TripDTO.class
     */
    TripDTO deleteTrip(Integer tripId);
}
