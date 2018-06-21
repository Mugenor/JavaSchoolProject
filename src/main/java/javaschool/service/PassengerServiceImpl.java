package javaschool.service;

import javaschool.dao.PassengerDAO;
import javaschool.entity.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("passengerService")
public class PassengerServiceImpl implements PassengerService {
    private PassengerDAO passengerDAO;
    @Autowired
    public PassengerServiceImpl(PassengerDAO passengerDAO) {
        this.passengerDAO = passengerDAO;
    }


    @Transactional(readOnly = true)
    public List<Passenger> getAllPassengers() {
        return passengerDAO.list();
    }
    @Transactional
    public void save(Passenger passenger) {
        passengerDAO.save(passenger);
    }
}
