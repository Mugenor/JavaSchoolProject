package javaschool.service.api;

import java.util.List;
import javaschool.entity.Departure;
import javaschool.entity.Passenger;
import javaschool.entity.Ticket;

public interface PassengerService {
    List<Passenger> getAllPassengers();
    void save(Passenger passenger);
    Passenger getById(Integer id);
    void buyTicket(String username, Integer departureId, Integer coachNumber, Integer seatNumber);
    void buyTicketTransactional(String username, Integer departureId, Integer coachNumber, Integer seatNumber);
    List<Passenger> findAllPassengersByDepartureId(Integer departureId);
}
