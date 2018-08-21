package javaschool.dao.api;

import javaschool.entity.Station;

/**
 * The interface Station dao.
 */
public interface StationDAO extends GenericDAO<Station, Integer> {
    /**
     * Find by title station.
     *
     * @param title the title
     * @return the station
     */
    Station findByTitle(String title);
}
