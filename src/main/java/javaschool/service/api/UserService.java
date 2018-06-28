package javaschool.service.api;

import java.util.List;
import javaschool.entity.User;

public interface UserService {
    void save(User user);
    User findById(int id);
    User findByUsername(String username);
    List<User> findAll();
}
