import javaschool.dao.api.PassengerDAO;
import javaschool.dao.api.UserDAO;
import javaschool.entity.Passenger;
import javaschool.entity.User;
import javaschool.service.api.UserService;
import javaschool.service.exception.EntityAlreadyExistsException;
import javaschool.service.impl.UserServiceImpl;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceTest {
    private static final String TEST_USERNAME = "username";
    private static final String TEST_NAME = "Name";
    private static final String TEST_SURNAME = "Surname";
    private static final LocalDate TEST_BIRTHDAY = LocalDate.parse("2000-01-01");
    private UserService userService;
//    @Mock
//    private UserDAO userDAO;
//    @Mock
//    private PassengerDAO passengerDAO;
//
//    @Before
//    public void init() {
//        userService = new UserServiceImpl(userDAO, passengerDAO);
//        User user = getInitializedUser();
//        when(userDAO.findByUsername(TEST_USERNAME)).thenReturn(user);
//        when(passengerDAO.findByNameAndSurnameAndBirthday(TEST_NAME, TEST_SURNAME, TEST_BIRTHDAY))
//                .thenReturn(user.getPassenger());
//    }
//
//    @Test
//    public void whenThereIsNoUserLikeWeWantToSave() {
//        User user = new User();
//        user.setUsername("anotherUsername");
//        Passenger passenger = getAnotherPassenger();
//        user.setPassenger(passenger);
//        userService.save(user);
//        verify(userDAO).findByUsername(user.getUsername());
//        verify(passengerDAO).findByNameAndSurnameAndBirthday
//                (passenger.getName(), passenger.getSurname(), passenger.getBirthday());
//        verify(userDAO).save(user);
//    }
//
//    @Test(expected = EntityAlreadyExistsException.class)
//    public void whenThereIsAlreadyUserWithSpecifiedUsername() {
//        User user = new User();
//        user.setUsername(TEST_USERNAME);
//        user.setPassenger(getAnotherPassenger());
//        userService.save(user);
//    }
//
//    @Test(expected = EntityAlreadyExistsException.class)
//    public void whenThereIsAlreadyPassengerWithSpecifiedNameAndSurnameAndBirthday() {
//        User user = new User();
//        user.setUsername("anotherUsername");
//        user.setPassenger(getInitializedPassenger());
//        userService.save(user);
//    }
//
//    private Passenger getAnotherPassenger() {
//        return new Passenger(TEST_NAME + "a", TEST_SURNAME + "a", TEST_BIRTHDAY);
//    }
//
//    private User getInitializedUser() {
//        User user = new User();
//        user.setId(1);
//        user.setUsername(TEST_USERNAME);
//        user.setPassenger(getInitializedPassenger());
//        return user;
//    }
//
//    private Passenger getInitializedPassenger() {
//        Passenger passenger = new Passenger(TEST_NAME, TEST_SURNAME, TEST_BIRTHDAY);
//        passenger.setId(1);
//        return passenger;
//    }

}
