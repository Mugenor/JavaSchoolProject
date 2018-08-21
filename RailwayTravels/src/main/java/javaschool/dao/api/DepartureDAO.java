package javaschool.dao.api;

import java.util.List;
import javaschool.entity.Departure;

/**
 * The interface Departure dao.
 */
public interface DepartureDAO extends GenericDAO<Departure, Integer> {
    /**
     * Find by id departure.
     *
     * @param id            the id
     * @param fetchStations the fetch stations
     * @return the departure
     */
    Departure findById(Integer id, boolean fetchStations);

    /**
     * Find all list.
     *
     * @param fetchStations       the fetch stations
     * @param orderByDateTimeFrom the order by date time from
     * @return the list
     */
    List<Departure> findAll(boolean fetchStations, boolean orderByDateTimeFrom);

    /**
     * Find by trip id and number in trip between list.
     *
     * @param tripId              the trip id
     * @param leftBound           the left bound
     * @param rightBound          the right bound
     * @param fetchStations       the fetch stations
     * @param orderByDateTimeFrom the order by date time from
     * @return the list
     */
    List<Departure> findByTripIdAndNumberInTripBetween(Integer tripId, Integer leftBound, Integer rightBound,
                                            boolean fetchStations, boolean orderByDateTimeFrom);
}
