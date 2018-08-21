package javaschool.dao.api;

import java.util.List;
import javaschool.entity.User;

/**
 * The interface User dao.
 */
public interface UserDAO extends GenericDAO<User, Integer>{
    /**
     * Find by username user.
     *
     * @param username the username
     * @return the user
     */
    User findByUsername(String username);

    /**
     * Find by email user.
     *
     * @param email the email
     * @return the user
     */
    User findByEmail(String email);

    /**
     * Find by username or email list.
     *
     * @param username the username
     * @param email    the email
     * @return the list
     */
    List<User> findByUsernameOrEmail(String username, String email);
}
