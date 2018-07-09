package javaschool.service.api;

import java.util.List;
import javaschool.entity.Passenger;

public interface PassengerService {
    List<Passenger> getAllPassengers();
    void save(Passenger passenger);
    Passenger getById(Integer id);
    void buyTicket(Integer passengerId, Integer ticketId);
    void buyTicketTransactional(Integer passengerId, Integer ticketId);
    List<Passenger> findAllPassengersByDepartureId(Integer departureId);
}
