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
     * Finds all departures which station of departure is stFrom,
     * arrival station is stTo and departure from dateTimeFrom to dateTimeTo.
     *
     * @param stFrom       Title of departure station
     * @param stTo         Title of arrival station
     * @param dateTimeFrom Left border of interval of departure DateTime
     * @param dateTimeTo   Right border of interval of departure DateTime
     * @return List of departures converted to DepartureDTO.class which station of departure is stFrom,
     * * arrival station is stTo and departure from dateTimeFrom to dateTimeTo.
     */
    List<DepartureDTO> findFromToBetween(String stFrom, String stTo, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo);

    /**
     * Finds all departures which station of departure is stFrom,
     * arrival station is stTo and departure from dateTimeFrom to dateTimeTo.
     *
     * @param stFrom       Title of departure station
     * @param stTo         Title of arrival station
     * @param dateTimeFrom Left border of interval of departure DateTime. Format is 'dd.MM.yyyy HH:mm'
     * @param dateTimeTo   Right border of interval of departure DateTime. Format is 'dd.MM.yyyy HH:mm'
     * @return List of departures converted to DepartureDTO.class which station of departure is stFrom,
     * * arrival station is stTo and departure from dateTimeFrom to dateTimeTo.
     */
    List<DepartureDTO> findFromToBetween(String stFrom, String stTo, String dateTimeFrom, String dateTimeTo);

    List<DepartureDTO> findAllToday();

    List<DepartureDTO> findAllAvailable(boolean fetchStations);

    /**
     * Finds all departures specified by the title of the station of departure
     *
     * @param stationTitle  Station title of departure
     * @param fetchStations If you want to work with departure's stations
     * @return List of departures converted to DepartureDTO.class specified by the title of the station of departure
     */
    List<DepartureDTO> findByStationTitle(String stationTitle, boolean fetchStations);

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
}
