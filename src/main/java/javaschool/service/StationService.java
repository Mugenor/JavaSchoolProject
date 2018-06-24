package javaschool.service;

import javaschool.entity.Departure;
import javaschool.entity.Station;

import java.util.List;

public interface StationService{
    void save(Station station);
    List<Departure> getAllDeparturesFrom(String stationTitle);
}
