package javaschool.dao.api;

import java.util.List;
import javaschool.entity.AlmostUser;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public interface AlmostUserDAO extends GenericDAO<AlmostUser,String> {
    List<AlmostUser> findByUsernameOrEmail(String username, String email);
    AlmostUser findByUsername(String username);
    AlmostUser findByEmail(String email);
    AlmostUser findByNameAndSurnameAndBirthday(String name, String surname, LocalDate birthday);
    int deleteByHash(String hash);
    int deleteRegisteredBefore(LocalDateTime dateTime);
}
