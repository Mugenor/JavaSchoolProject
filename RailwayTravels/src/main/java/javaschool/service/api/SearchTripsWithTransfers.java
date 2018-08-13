package javaschool.service.api;

import java.util.List;
import javaschool.controller.dtoentity.TripDTO;
import org.joda.time.LocalDateTime;

public interface SearchTripsWithTransfers {

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
}
