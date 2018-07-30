package javaschool.service.converter;

import javaschool.controller.dtoentity.StationName;
import javaschool.entity.Station;
import org.springframework.stereotype.Service;

@Service
public class StationToStationNameConverter implements ClassConverter<Station, StationName> {
    @Override
    public StationName convertTo(Station station) {
        return new StationName(station.getTitle());
    }

    @Override
    public Station convertFrom(StationName stationName) {
        return new Station(stationName.getName());
    }
}
