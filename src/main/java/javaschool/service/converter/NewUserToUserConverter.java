package javaschool.service.converter;

import javaschool.controller.dtoentity.NewUser;
import javaschool.entity.Passenger;
import javaschool.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class NewUserToUserConverter {
    private PasswordEncoder passwordEncoder;

    @Autowired
    public NewUserToUserConverter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User convert(NewUser newUser) {
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
}
