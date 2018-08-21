package javaschool.service.api;

import javaschool.entity.AlmostUser;

/**
 * The interface Almost user service.
 */
public interface AlmostUserService {
    /**
     * Saving almost user if there is no other users with same data
     *
     * @param almostUser almost user to save
     */
    void save(AlmostUser almostUser);

    /**
     * Returning almost user by it's hashId if time from saving almost user isn't more than 10 minutes
     *
     * @param hash almost user's hash
     * @return almost user with specified hashId
     */
    AlmostUser findByHash(String hash);

    /**
     * Deleting almost user by it's hashId
     *
     * @param hash almost user's hashId
     * @return true if almost user was deleted. Otherwise - false
     */
    boolean deleteByHash(String hash);

    /**
     * Cron task which delete old entries of almost users every midnight
     */
    void removeOldEntries();
}
