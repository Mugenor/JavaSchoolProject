package javaschool.service.api;

import java.util.List;
import javaschool.controller.dtoentity.StationName;
import javaschool.entity.Station;

/**
 * The interface Station service.
 */
public interface StationService {
    /**
     * Saves new station
     *
     * @param station Station to be saved
     */
    void save(Station station);

    /**
     * Saves new station with specified title
     *
     * @param title Station's title
     */
    void save(String title);

    /**
     * Saves new station with specified title. It does not throw custom exception.
     * Instead of it method can throw DataIntegrityViolationException.class. It's recommended to use save method
     *
     * @param title Station title
     */
    void saveTransactional(String title);

    /**
     * Saves new station. It does not throw custom exception.
     * Instead of it method can throw DataIntegrityViolationException.class. It's recommended to use save method
     *
     * @param station Station title
     */
    void saveTransactional(Station station);

    /**
     * Finds all stations
     *
     * @return List of all stations
     */
    List<Station> findAll();

    /**
     * Finds all stations and convert it to StationName.class
     *
     * @return List of stations converted to StationName.class
     */
    List<StationName> findAllStationNames();
}
