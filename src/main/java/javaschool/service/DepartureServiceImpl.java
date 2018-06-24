package javaschool.service;

import javaschool.dao.DepartureDAO;
import javaschool.dao.StationDAO;
import javaschool.entity.Departure;
import javaschool.entity.Station;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartureServiceImpl implements DepartureService {
    private DepartureDAO departureDAO;
    private StationDAO stationDAO;

    @Autowired
    public DepartureServiceImpl(DepartureDAO departureDAO, StationDAO stationDAO) {
        this.stationDAO = stationDAO;
        this.departureDAO = departureDAO;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Departure> findFromToBetween(String stFrom, String stTo, LocalDateTime dateFrom, LocalDateTime dateTo) {
        Station stationFrom = stationDAO.findByTitle(stFrom);
        Station stationTo = stationDAO.findByTitle(stTo);
        return departureDAO.findFromToBetween(stationFrom, stationTo, dateFrom, dateTo);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Departure> findAll() {
        return departureDAO.findAll();
    }

    @Override
    @Transactional
    public void save(Departure departure) {
        departureDAO.save(departure);
    }
}
