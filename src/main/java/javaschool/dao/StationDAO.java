package javaschool.dao;

import javaschool.entity.Station;

public interface StationDAO extends GenericDAO<Station, Integer> {
    Station findByTitle(String title);
}
