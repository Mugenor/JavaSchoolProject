package javaschool.service.api;

import java.util.List;
import javaschool.controller.dtoentity.DepartureDTO;
import javaschool.controller.dtoentity.NewDepartureDTO;
import javaschool.entity.Departure;
import org.joda.time.LocalDateTime;

public interface DepartureService {
    /**
     * Finds departure by id and returns it converted to DepartureDTO.class
     *
     * @param id            Departure id
     * @param fetchStations If you want to work with departure's stations
     * @return Departure with transmitted id and converted to DepartureDTO.class
     * or null if there is no departure with specified id
     */
    DepartureDTO findById(Integer id, boolean fetchStations);

    /**
     * Finds departure by id and returns it. This method does not convert departure to DepartureDTO.class
     *
     * @param id            Departure id
     * @param fetchStations If you want to work with departure's stations
     * @return Departure with transmitted id
     * or null if there is no departure with specified id
     */
    Departure findByIdRaw(Integer id, boolean fetchStations);

    /**
     * Finds all departures
     *
     * @param fetchStations If you want to work with departure's stations
     * @return List of departures converted to DepartureDTO.class
     */
    List<DepartureDTO> findAll(boolean fetchStations);
    /**
     * Saves new departure with specified parameters.
     *
     * @param coachesCount Count of coaches from which the train will consists
     * @param stationFrom  Station title of departure
     * @param stationTo    Title of arrival station
     * @param dateTimeFrom Departure time
     * @param dateTimeTo   Arrival time
     * @return Saved departure
     */
    Departure save(int coachesCount, String stationFrom, String stationTo, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo);

    /**
     * Saves new departure with parameters specified in newDepartureDTO object
     *
     * @param newDepartureDTO Object which contains parameters for saving new departure
     * @return Saved departure
     */
    Departure save(NewDepartureDTO newDepartureDTO);

    void updateDeparture(Integer tripId, Integer departureIndex, NewDepartureDTO departureDTO);
}
