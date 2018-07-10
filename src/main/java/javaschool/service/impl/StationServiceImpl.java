package javaschool.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import javaschool.controller.dtoentity.StationName;
import javaschool.dao.api.StationDAO;
import javaschool.entity.Departure;
import javaschool.entity.Station;
import javaschool.service.api.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StationServiceImpl implements StationService {
    private StationDAO stationDAO;

    @Autowired
    public StationServiceImpl(StationDAO stationDAO) {
        this.stationDAO = stationDAO;
    }

    @Override
    @Transactional
    public void save(Station station) {
        stationDAO.save(station);
    }

    @Override
    @Transactional
    public void save(String title) {
        Station station = new Station();
        station.setTitle(title);
        stationDAO.save(station);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Departure> getAllDeparturesFrom(String stationTitle) {
        return stationDAO.findByTitle(stationTitle).getDepartures();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Station> findAll() {
        return stationDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StationName> findAllStationNames() {
        return findAll().parallelStream()
                .map(station -> new StationName(station.getTitle()))
                .collect(Collectors.toList());
    }
}
