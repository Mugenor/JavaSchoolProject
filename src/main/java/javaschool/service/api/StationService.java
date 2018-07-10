package javaschool.service.api;

import java.util.List;
import javaschool.controller.dtoentity.StationName;
import javaschool.entity.Departure;
import javaschool.entity.Station;

public interface StationService{
    void save(Station station);
    void save(String title);
    List<Station> findAll();
    List<StationName> findAllStationNames();
    List<Departure> getAllDeparturesFrom(String stationTitle);
}
