package javaschool.service.converter;

import javaschool.controller.dtoentity.NewUser;
import javaschool.entity.AlmostUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The type New user to almost user converter.
 */
@Service
public class NewUserToAlmostUserConverter implements ClassConverter<NewUser, AlmostUser> {
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Override
    public AlmostUser convertTo(NewUser newUser) {
        String hash = encoder.encode(newUser.getUsername()).replaceAll("[^a-zA-Z]", "");
        return new AlmostUser(hash, encoder.encode(newUser.getPassword()), newUser.getUsername(), newUser.getEmail(),
                newUser.getName(), newUser.getSurname(), newUser.getBirthday());
    }

    @Override
    public NewUser convertFrom(AlmostUser almostUser) {
        return new NewUser(almostUser.getName(), almostUser.getSurname(),
                almostUser.getUsername(), almostUser.getPassword(), almostUser.getEmail(),
                almostUser.getBirthday());
    }
}
