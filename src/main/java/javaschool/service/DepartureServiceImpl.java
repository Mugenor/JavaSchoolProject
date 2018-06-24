package javaschool.service;

import javaschool.dao.DepartureDAO;
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

    @Autowired
    public DepartureServiceImpl(DepartureDAO departureDAO) {
        this.departureDAO = departureDAO;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Departure> findFromToBetween(String stFrom, String stTo, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return departureDAO.findFromToBetween(stFrom, stTo, dateFrom, dateTo);
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
