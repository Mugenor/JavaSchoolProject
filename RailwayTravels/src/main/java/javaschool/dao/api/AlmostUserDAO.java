package javaschool.dao.api;

import java.util.List;
import javaschool.entity.AlmostUser;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public interface AlmostUserDAO extends GenericDAO<AlmostUser,String> {
    List<AlmostUser> findByUsernameOrEmail(String username, String email);
    AlmostUser findByIdAndAfter(String id, LocalDateTime after);
    AlmostUser findByUsernameAndAfter(String username, LocalDateTime after);
    AlmostUser findByEmailAndAfter(String email, LocalDateTime after);
    AlmostUser findByNameAndSurnameAndBirthdayAndAfter(String name, String surname, LocalDate birthday, LocalDateTime after);
    int deleteByHash(String hash);
    int deleteRegisteredBefore(LocalDateTime dateTime);
}
