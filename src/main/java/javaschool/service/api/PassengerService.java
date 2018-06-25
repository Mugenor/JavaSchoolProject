package javaschool.service.api;

import javaschool.entity.Passenger;

import java.util.List;

public interface PassengerService {
    List<Passenger> getAllPassengers();
    void save(Passenger passenger);
    Passenger getById(Integer id);
    void buyTicket(Integer passengerId, Integer ticketId);
    List<Passenger> findAllPassengersByDepartureId(Integer departureId);
}
