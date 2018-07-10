package javaschool.dao.api;

import javaschool.entity.Ticket;
import javaschool.entity.User;

public interface UserDAO extends GenericDAO<User, Integer>{
    User findByUsername(String username);
}
