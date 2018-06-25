package javaschool.dao.api;

import javaschool.entity.Passenger;
import javaschool.entity.Ticket;
import org.joda.time.LocalDate;

public interface PassengerDAO extends GenericDAO<Passenger, Integer> {
    Passenger findByNameAndSurnameAndBirthday(String name, String surname, LocalDate birthday);
    void buyTicket(Passenger passenger, Ticket ticket);
}
