package javaschool.service.impl;

import javaschool.dao.api.AlmostUserDAO;
import javaschool.dao.api.PassengerDAO;
import javaschool.dao.api.UserDAO;
import javaschool.entity.AlmostUser;
import javaschool.service.api.AlmostUserService;
import javaschool.service.exception.EntityAlreadyExistsException;
import org.joda.time.Duration;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlmostUserServiceImpl implements AlmostUserService {
    public static final Duration EXPIRED_DELAY = Duration.standardMinutes(10);

    private AlmostUserDAO almostUserDAO;
    private UserDAO userDAO;
    private PassengerDAO passengerDAO;

    @Autowired
    public AlmostUserServiceImpl(AlmostUserDAO almostUserDAO, UserDAO userDAO,
                                 PassengerDAO passengerDAO) {
        this.almostUserDAO = almostUserDAO;
        this.userDAO = userDAO;
        this.passengerDAO = passengerDAO;
    }

    @Override
    @Transactional
    public void save(AlmostUser almostUser) {
        LocalDateTime expiredTime = LocalDateTime.now().minus(EXPIRED_DELAY);
        if(userDAO.findByUsername(almostUser.getUsername()) != null ||
                almostUserDAO.findByUsernameAndAfter(almostUser.getUsername(),expiredTime) != null) {
            throw new EntityAlreadyExistsException("User with \"" + almostUser.getUsername() + "\" username already exists!");
        } else if(userDAO.findByEmail(almostUser.getEmail()) != null
                || almostUserDAO.findByEmailAndAfter(almostUser.getEmail(), expiredTime) != null) {
            throw new EntityAlreadyExistsException("User with \"" + almostUser.getEmail() + "\" email already exists!");
        } else if(passengerDAO.findByNameAndSurnameAndBirthday
                (almostUser.getName(), almostUser.getSurname(), almostUser.getBirthday()) != null ||
                almostUserDAO.findByNameAndSurnameAndBirthdayAndAfter(almostUser.getName(), almostUser.getSurname(), almostUser.getBirthday(), expiredTime) != null) {
            throw new EntityAlreadyExistsException("Passenger " + almostUser.getName() + " " + almostUser.getSurname() +
                    " " + almostUser.getBirthday().toString("dd.MM.yyyy") + " date of birth already exists!");
        }
        almostUser.setRegistered(LocalDateTime.now());
        almostUserDAO.save(almostUser);
    }

    @Override
    @Transactional(readOnly = true)
    public AlmostUser findByHash(String hash) {
        return almostUserDAO.findByIdAndAfter(hash,
                LocalDateTime.now().minus(EXPIRED_DELAY));
    }

    @Override
    @Transactional
    public int deleteByHash(String hash) {
        return almostUserDAO.deleteByHash(hash);
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void removeOldEntries() {
        almostUserDAO.deleteRegisteredBefore(LocalDateTime.now().minus(EXPIRED_DELAY));
    }
}
