package javaschool.service.converter;

import javaschool.entity.AlmostUser;
import javaschool.entity.Passenger;
import javaschool.entity.User;
import org.springframework.stereotype.Service;

@Service
public class AlmostUserToUserConverter implements ClassConverter<AlmostUser, User> {
    @Override
    public User convertTo(AlmostUser almostUser) {
        User user = new User();
        user.setUsername(almostUser.getUsername());
        user.setPassword(almostUser.getPassword());
        user.setEmail(almostUser.getEmail());
        user.setPassenger(new Passenger(almostUser.getName(), almostUser.getSurname(), almostUser.getBirthday()));
        return user;
    }

    @Override
    public AlmostUser convertFrom(User user) {
        return new AlmostUser(null, user.getPassword(), user.getUsername(), user.getEmail(), user.getPassenger().getName(),
                user.getPassenger().getSurname(), user.getPassenger().getBirthday());
    }
}
