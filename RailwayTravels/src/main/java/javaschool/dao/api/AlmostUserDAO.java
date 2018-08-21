package javaschool.dao.api;

import java.util.List;
import javaschool.entity.AlmostUser;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

/**
 * The interface Almost user dao.
 */
public interface AlmostUserDAO extends GenericDAO<AlmostUser,String> {
    /**
     * Find by username or email list.
     *
     * @param username the username
     * @param email    the email
     * @return the list
     */
    List<AlmostUser> findByUsernameOrEmail(String username, String email);

    /**
     * Find by id and after almost user.
     *
     * @param id    the id
     * @param after the after
     * @return the almost user
     */
    AlmostUser findByIdAndAfter(String id, LocalDateTime after);

    /**
     * Find by username and after almost user.
     *
     * @param username the username
     * @param after    the after
     * @return the almost user
     */
    AlmostUser findByUsernameAndAfter(String username, LocalDateTime after);

    /**
     * Find by email and after almost user.
     *
     * @param email the email
     * @param after the after
     * @return the almost user
     */
    AlmostUser findByEmailAndAfter(String email, LocalDateTime after);

    /**
     * Find by name and surname and birthday and after almost user.
     *
     * @param name     the name
     * @param surname  the surname
     * @param birthday the birthday
     * @param after    the after
     * @return the almost user
     */
    AlmostUser findByNameAndSurnameAndBirthdayAndAfter(String name, String surname, LocalDate birthday, LocalDateTime after);

    /**
     * Delete by hash int.
     *
     * @param hash the hash
     * @return the int
     */
    int deleteByHash(String hash);

    /**
     * Delete registered before int.
     *
     * @param dateTime the date time
     * @return the int
     */
    int deleteRegisteredBefore(LocalDateTime dateTime);
}
