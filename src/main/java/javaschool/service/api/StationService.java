package javaschool.service.api;

import java.util.List;
import javaschool.entity.Departure;
import javaschool.entity.Station;

public interface StationService{
    void save(Station station);
    void save(String title);
    List<Departure> getAllDeparturesFrom(String stationTitle);
}
