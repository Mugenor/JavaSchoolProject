package javaschool.service;

import javaschool.entity.Passenger;

import java.util.List;

public interface PassengerService {
    List<Passenger> getAllPassengers();
    void save(Passenger passenger);
    Passenger getById(Integer id);
}
