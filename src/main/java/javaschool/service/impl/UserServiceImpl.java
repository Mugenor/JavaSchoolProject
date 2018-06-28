package javaschool.service.impl;

import java.util.List;
import javaschool.dao.api.PassengerDAO;
import javaschool.dao.api.UserDAO;
import javaschool.entity.Passenger;
import javaschool.entity.User;
import javaschool.service.api.UserService;
import javaschool.service.exception.EntityAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;
    private PassengerDAO passengerDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, PassengerDAO passengerDAO) {
        this.userDAO = userDAO;
        this.passengerDAO = passengerDAO;
    }

    @Transactional
    @Override
    public void save(User user) {
        Passenger passenger = user.getPassenger();
        if(userDAO.findByUsername(user.getUsername()) != null) {
            throw new EntityAlreadyExistsException("User with " + user.getUsername() + " username already exists");
        } else if(passengerDAO.findByNameAndSurnameAndBirthday
                (passenger.getName(), passenger.getSurname(), passenger.getBirthday()) != null) {
            throw new EntityAlreadyExistsException("Passenger " + passenger.getName() + " " + passenger.getSurname() +
                                " " + passenger.getBirthday().toString("dd.mm.yyyy") + " date of birth");
        }

        passengerDAO.save(passenger);
        userDAO.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(int id) {
        return userDAO.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }
}
