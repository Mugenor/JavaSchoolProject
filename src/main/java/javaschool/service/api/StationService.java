package javaschool.service.api;

import javaschool.entity.Departure;
import javaschool.entity.Station;

import java.util.List;

public interface StationService{
    void save(Station station);
    void save(String title);
    List<Departure> getAllDeparturesFrom(String stationTitle);
}
