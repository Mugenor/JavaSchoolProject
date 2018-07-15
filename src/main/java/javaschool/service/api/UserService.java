package javaschool.service.api;

import java.util.List;
import javaschool.entity.User;

public interface UserService {
    /**
     * Saves new user
     *
     * @param user User to be saved
     */
    void save(User user);

    /**
     * Finds user by id
     *
     * @param id User's id
     * @return User with specified id or null if there is no user with specified id
     */
    User findById(int id);

    /**
     * Finds user by username
     *
     * @param username User's username
     * @return User with specified username or null if there is no user with specified username
     */
    User findByUsername(String username);

    /**
     * Finds all users
     *
     * @return List with all users
     */
    List<User> findAll();
}
