package javaschool.service.impl;

import javaschool.dao.api.PassengerDAO;
import javaschool.entity.Passenger;
import javaschool.service.api.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service()
public class PassengerServiceImpl implements PassengerService {
    private PassengerDAO passengerDAO;
    @Autowired
    public PassengerServiceImpl(PassengerDAO passengerDAO) {
        this.passengerDAO = passengerDAO;
    }


    @Transactional(readOnly = true)
    public List<Passenger> getAllPassengers() {
        return passengerDAO.findAll();
    }
    @Transactional
    public void save(Passenger passenger) {
        passengerDAO.save(passenger);
    }

    @Override
    @Transactional(readOnly = true)
    public Passenger getById(Integer id) {
        return passengerDAO.findById(id);
    }
}
