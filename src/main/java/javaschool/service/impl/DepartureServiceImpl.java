package javaschool.service.impl;

import java.util.LinkedList;
import java.util.List;
import javaschool.dao.api.DepartureDAO;
import javaschool.dao.api.StationDAO;
import javaschool.entity.Departure;
import javaschool.entity.Station;
import javaschool.entity.Ticket;
import javaschool.service.api.DepartureService;
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
    public void save(int sitsCount, String stationFrom, String stationTo, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        Departure departure = new Departure();
        Station station = stationDAO.findByTitle(stationFrom);
        station.getDepartures().add(departure);

        departure.setSitsCount(sitsCount);
        departure.setFreeSitsCount(sitsCount);
        departure.setStationFrom(station);
        departure.setStationTo(stationDAO.findByTitle(stationTo));
        departure.setDateTimeFrom(dateTimeFrom);
        departure.setDateTimeTo(dateTimeTo);

        List<Ticket> tickets = new LinkedList<>();
        for(int i=1; i <= sitsCount; ++i) {
            Ticket ticket = new Ticket();
            ticket.setDeparture(departure);
            ticket.setSiteNum(i);
            tickets.add(ticket);
        }
        departure.setTickets(tickets);

        departureDAO.save(departure);
    }
}
