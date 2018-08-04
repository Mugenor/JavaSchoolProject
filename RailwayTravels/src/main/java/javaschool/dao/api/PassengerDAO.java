package javaschool.dao.api;

import java.util.List;
import javaschool.entity.Passenger;
import org.joda.time.LocalDate;

public interface PassengerDAO extends GenericDAO<Passenger, Integer> {
    Passenger findByUsername(String username);
    Passenger findByNameAndSurnameAndBirthday(String name, String surname, LocalDate birthday);
    List<Passenger> findAllPassengersByDepartureId(Integer departureId);
    List<Passenger> findAllPassengersByTripId(Integer departureId);
}
