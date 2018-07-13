package javaschool.service.api;

import java.util.List;
import javaschool.controller.dtoentity.PassengerWithoutTickets;
import javaschool.entity.Passenger;

public interface PassengerService {
    List<PassengerWithoutTickets> getAllPassengers();
    void save(Passenger passenger);
    Passenger getById(Integer id);
    void buyTicket(String username, Integer departureId, Integer coachNumber, Integer seatNumber);
    void buyTicketTransactional(String username, Integer departureId, Integer coachNumber, Integer seatNumber);
    List<PassengerWithoutTickets> findAllPassengersByDepartureId(Integer departureId);
}
