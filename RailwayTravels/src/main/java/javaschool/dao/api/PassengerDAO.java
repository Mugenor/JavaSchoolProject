package javaschool.dao.api;

import java.util.List;
import javaschool.entity.Passenger;
import org.joda.time.LocalDate;

/**
 * The interface Passenger dao.
 */
public interface PassengerDAO extends GenericDAO<Passenger, Integer> {
    /**
     * Find by username passenger.
     *
     * @param username the username
     * @return the passenger
     */
    Passenger findByUsername(String username);

    /**
     * Find by name and surname and birthday passenger.
     *
     * @param name     the name
     * @param surname  the surname
     * @param birthday the birthday
     * @return the passenger
     */
    Passenger findByNameAndSurnameAndBirthday(String name, String surname, LocalDate birthday);

    /**
     * Find all passengers by departure id list.
     *
     * @param departureId the departure id
     * @return the list
     */
    List<Passenger> findAllPassengersByDepartureId(Integer departureId);

    /**
     * Find all passengers by trip id list.
     *
     * @param departureId the departure id
     * @return the list
     */
    List<Passenger> findAllPassengersByTripId(Integer departureId);

    /**
     * Find all passengers by trip id and departure index bounds list.
     *
     * @param tripId the trip id
     * @param from   the from
     * @param to     the to
     * @return the list
     */
    List<Passenger> findAllPassengersByTripIdAndDepartureIndexBounds(Integer tripId, Integer from, Integer to);
}
