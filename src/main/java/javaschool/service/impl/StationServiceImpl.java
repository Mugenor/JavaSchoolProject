package javaschool.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import javaschool.controller.dtoentity.StationName;
import javaschool.dao.api.StationDAO;
import javaschool.entity.Departure;
import javaschool.entity.Station;
import javaschool.service.api.StationService;
import javaschool.service.exception.EntityAlreadyExistsException;
import javaschool.service.exception.InvalidStationTitleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StationServiceImpl implements StationService {
    private static final String TITLE_NULL_MESSAGE = "Station title must not be null!";
    private static final String STATION_EXISTS_EXC = "Station already exists!";
    private StationDAO stationDAO;
    private StationService selfProxy;

    @Autowired
    public StationServiceImpl(StationDAO stationDAO) {
        this.stationDAO = stationDAO;
    }

    @Autowired
    public void setSelfProxy(StationService selfProxy) {
        this.selfProxy = selfProxy;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveTransactional(Station station) {
        stationDAO.save(station);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveTransactional(String title) {
        Station station = new Station();
        station.setTitle(title);
        stationDAO.save(station);
    }

    @Override
    @Transactional(readOnly = true)
    public void save(String title){
        try {
            selfProxy.saveTransactional(title);
        } catch (DataIntegrityViolationException e) {
            throw new EntityAlreadyExistsException("Station with title \"" + title + "\" already exists!");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void save(Station station) {
        if(station.getTitle() == null) {
            throw new InvalidStationTitleException(TITLE_NULL_MESSAGE);
        }
        try {
            selfProxy.saveTransactional(station);
        } catch (DataIntegrityViolationException e) {
            throw new EntityAlreadyExistsException(STATION_EXISTS_EXC);
        }
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
