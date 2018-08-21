package javaschool.service.converter;

import javaschool.controller.dtoentity.NewUser;
import javaschool.entity.Passenger;
import javaschool.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The type New user to user converter.
 */
@Service
public class NewUserToUserConverter implements ClassConverter<NewUser, User> {
    private PasswordEncoder passwordEncoder;

    /**
     * Instantiates a new New user to user converter.
     *
     * @param passwordEncoder the password encoder
     */
    @Autowired
    public NewUserToUserConverter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User convertTo(NewUser newUser) {
        User user = new User();
        Passenger passenger = new Passenger();
        passenger.setName(newUser.getName());
        passenger.setSurname(newUser.getSurname());
        passenger.setBirthday(newUser.getBirthday());

        user.setUsername(newUser.getUsername());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setPassenger(passenger);

        return user;
    }

    @Override
    public NewUser convertFrom(User user) {
        NewUser newUser = new NewUser();
        newUser.setUsername(user.getUsername());
        if(user.getPassenger() != null) {
            newUser.setName(user.getPassenger().getName());
            newUser.setSurname(user.getPassenger().getSurname());
            newUser.setBirthday(user.getPassenger().getBirthday());
        }
        return newUser;
    }
}
