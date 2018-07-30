package javaschool.dao.api;

import java.util.List;
import javaschool.entity.User;

public interface UserDAO extends GenericDAO<User, Integer>{
    User findByUsername(String username);
    User findByEmail(String email);
    List<User> findByUsernameOrEmail(String username, String email);
}
