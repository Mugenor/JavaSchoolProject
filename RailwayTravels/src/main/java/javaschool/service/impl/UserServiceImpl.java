package javaschool.service.impl;

import java.util.List;
import javaschool.dao.api.AlmostUserDAO;
import javaschool.dao.api.PassengerDAO;
import javaschool.dao.api.UserDAO;
import javaschool.entity.AlmostUser;
import javaschool.entity.Passenger;
import javaschool.entity.User;
import javaschool.service.api.UserService;
import javaschool.service.converter.AlmostUserToUserConverter;
import javaschool.service.exception.EntityAlreadyExistsException;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static javaschool.service.impl.AlmostUserServiceImpl.EXPIRED_DELAY;

/**
 * The type User service.
 */
@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;
    private PassengerDAO passengerDAO;
    private AlmostUserDAO almostUserDAO;
    private AlmostUserToUserConverter almostUserToUserConverter;

    /**
     * Instantiates a new User service.
     *
     * @param userDAO                   the user dao
     * @param passengerDAO              the passenger dao
     * @param almostUserDAO             the almost user dao
     * @param almostUserToUserConverter the almost user to user converter
     */
    @Autowired
    public UserServiceImpl(UserDAO userDAO, PassengerDAO passengerDAO, AlmostUserDAO almostUserDAO,
                           AlmostUserToUserConverter almostUserToUserConverter) {
        this.userDAO = userDAO;
        this.passengerDAO = passengerDAO;
        this.almostUserDAO = almostUserDAO;
        this.almostUserToUserConverter = almostUserToUserConverter;
    }

    @Transactional
    @Override
    public void save(User user) {
        LocalDateTime after = LocalDateTime.now().minus(EXPIRED_DELAY);
        Passenger passenger = user.getPassenger();
        if(userDAO.findByUsername(user.getUsername()) != null
                || almostUserDAO.findByUsernameAndAfter(user.getUsername(), after) != null) {
            throw new EntityAlreadyExistsException("User with \"" + user.getUsername() + "\" username already exists!");
        } else if(userDAO.findByEmail(user.getEmail()) != null
                || almostUserDAO.findByEmailAndAfter(user.getEmail(), after) != null) {
            throw new EntityAlreadyExistsException("User with \"" + user.getEmail() + "\" email already exists!");
        } else if(passenger != null && (passengerDAO.findByNameAndSurnameAndBirthday
                (passenger.getName(), passenger.getSurname(), passenger.getBirthday()) != null ||
                almostUserDAO.findByNameAndSurnameAndBirthdayAndAfter(passenger.getName(), passenger.getSurname(), passenger.getBirthday(), after) != null)) {
            throw new EntityAlreadyExistsException("Passenger " + passenger.getName() + " " + passenger.getSurname() +
                    " " + passenger.getBirthday().toString("dd.MM.yyyy") + " date of birth already exists!");
        }

        userDAO.save(user);
    }

    @Override
    @Transactional
    public void save(AlmostUser almostUser) {
        save(almostUserToUserConverter.convertTo(almostUser));
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
