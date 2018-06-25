package javaschool.service.impl;

import javaschool.dao.api.StationDAO;
import javaschool.entity.Departure;
import javaschool.entity.Station;
import javaschool.service.api.StationService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        List<Departure> departures = stationDAO.findByTitle(stationTitle).getDepartures();
        Hibernate.initialize(departures);
        return departures;
    }
}
