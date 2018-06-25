package javaschool.service.api;

import javaschool.entity.Passenger;
import javaschool.entity.Ticket;

import java.util.List;

public interface PassengerService {
    List<Passenger> getAllPassengers();
    void save(Passenger passenger);
    Passenger getById(Integer id);
    void buyTicket(Integer passengerId, Integer ticketId);
}
