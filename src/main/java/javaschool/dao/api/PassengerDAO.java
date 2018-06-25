package javaschool.dao.api;

import javaschool.entity.Passenger;
import org.joda.time.LocalDate;

import java.util.List;

public interface PassengerDAO extends GenericDAO<Passenger, Integer> {
    Passenger findByNameAndSurnameAndBirthday(String name, String surname, LocalDate birthday);
    List<Passenger> findAllPassengersByDepartureId(Integer departureId);
}
