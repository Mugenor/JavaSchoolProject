package javaschool.dao.api;

import javaschool.entity.User;

public interface UserDAO extends GenericDAO<User, Integer>{
    User findByUsername(String username);
}
