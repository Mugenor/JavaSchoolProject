package javaschool.dao;


import javaschool.entity.Passenger;

import java.util.List;

public interface PassengerDAO {
    void save(Passenger passanger);
    List<Passenger> list();
}
