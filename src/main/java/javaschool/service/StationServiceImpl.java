package javaschool.service;

import javaschool.dao.GenericAbstractDAO;
import javaschool.dao.StationDAO;
import javaschool.entity.Station;
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
}
