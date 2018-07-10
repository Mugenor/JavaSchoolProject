package javaschool.service.impl;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import javaschool.dao.api.DepartureDAO;
import javaschool.dao.api.StationDAO;
import javaschool.entity.Coach;
import javaschool.entity.Departure;
import javaschool.entity.Station;
import javaschool.entity.Ticket;
import javaschool.service.api.DepartureService;
import javaschool.service.exception.NoSuchEntityException;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartureServiceImpl implements DepartureService {
    private DepartureDAO departureDAO;
    private StationDAO stationDAO;

    @Autowired
    public DepartureServiceImpl(DepartureDAO departureDAO, StationDAO stationDAO) {
        this.stationDAO = stationDAO;
        this.departureDAO = departureDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public Departure findById(Integer id, boolean fetchStations, boolean fetchTickets) {
        Departure departure = departureDAO.findById(id, fetchStations, fetchTickets);
        if(departure == null) {
            throw new NoSuchEntityException("Departure is not found!", Departure.class);
        }
        return departure;
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
    public List<Departure> findAll(boolean fetchStations, boolean fetchTickets) {
        return departureDAO.findAll(fetchStations, fetchTickets);
    }

    @Override
    @Transactional
    public void save(int coachesNum, String stationFrom, String stationTo, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        Departure departure = new Departure();
        Station station = stationDAO.findByTitle(stationFrom);
        station.getDepartures().add(departure);

        departure.setSitsCount(coachesNum * Coach.DEFAULT_SEATS_NUM);
        departure.setFreeSitsCount(coachesNum * Coach.DEFAULT_SEATS_NUM);
        departure.setStationFrom(station);
        departure.setStationTo(stationDAO.findByTitle(stationTo));
        departure.setDateTimeFrom(dateTimeFrom);
        departure.setDateTimeTo(dateTimeTo);

        LinkedHashSet<Coach> coaches = new LinkedHashSet<>();
        for(int i=1; i <= coachesNum; ++i) {
            Coach coach = new Coach();
            coach.setDeparture(departure);
            coach.setCoachNumber(i);
            LinkedHashSet<Ticket> tickets = new LinkedHashSet<>();
            for(int j = 1; j <= Coach.DEFAULT_SEATS_NUM; ++j) {
                Ticket ticket = new Ticket();
                ticket.setCoach(coach);
                ticket.setSiteNum(j);
                tickets.add(ticket);
            }
            coach.setTickets(tickets);
        }
        departure.setCoaches(coaches);

        departureDAO.save(departure);
    }
}
