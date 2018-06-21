package javaschool.service;

import javaschool.dao.PassengerDAO;
import javaschool.entity.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("passengerService")
@Transactional
public class PassengerService {
    private PassengerDAO passengerDAO;
    @Autowired
    public PassengerService(PassengerDAO passengerDAO) {
        this.passengerDAO = passengerDAO;
    }


    public List<Passenger> getAllPassengers() {
        return passengerDAO.list();
    }

    public void save(Passenger passenger) {
        passengerDAO.save(passenger);
    }
}
