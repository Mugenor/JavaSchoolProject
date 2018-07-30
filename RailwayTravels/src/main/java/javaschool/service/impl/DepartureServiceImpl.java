package javaschool.service.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import javaschool.controller.dtoentity.DepartureDTO;
import javaschool.controller.dtoentity.NewDepartureDTO;
import javaschool.dao.api.DepartureDAO;
import javaschool.dao.api.StationDAO;
import javaschool.entity.Coach;
import javaschool.entity.Departure;
import javaschool.entity.Station;
import javaschool.entity.Ticket;
import javaschool.service.api.DepartureService;
import javaschool.service.converter.DepartureToDepartureDTOConverter;
import javaschool.service.converter.StringToLocalDateTimeConverter;
import javaschool.service.exception.NoSuchEntityException;
import javaschool.service.exception.StationEqualsException;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartureServiceImpl implements DepartureService {
    private StringToLocalDateTimeConverter stringToLocalDateTimeConverter;
    private DepartureToDepartureDTOConverter departureToDepartureDTOConverter;
    private DepartureDAO departureDAO;
    private StationDAO stationDAO;
    private RabbitTemplate rabbitTemplate;
    private DepartureService selfProxy;

    @Autowired
    public DepartureServiceImpl(DepartureDAO departureDAO, StationDAO stationDAO,
                                StringToLocalDateTimeConverter converter, DepartureToDepartureDTOConverter departureDTOConverter,
                                RabbitTemplate rabbitTemplate) {
        this.stationDAO = stationDAO;
        this.departureDAO = departureDAO;
        this.stringToLocalDateTimeConverter = converter;
        this.departureToDepartureDTOConverter = departureDTOConverter;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setSelfProxy(DepartureService selfProxy) {
        this.selfProxy = selfProxy;
    }

    @Override
    @Transactional(readOnly = true)
    public DepartureDTO findById(Integer id, boolean fetchStations, boolean fetchTickets) {
        return departureToDepartureDTOConverter.convertTo(findByIdRaw(id, fetchStations, fetchTickets));
    }

    @Override
    @Transactional(readOnly = true)
    public Departure findByIdRaw(Integer id, boolean fetchStations, boolean fetchTickets) {
        Departure departure = departureDAO.findById(id, fetchStations, fetchTickets);
        if (departure == null) {
            throw new NoSuchEntityException("Departure is not found!", Departure.class);
        }
        return departure;
    }

    @Transactional(readOnly = true)
    @Override
    public List<DepartureDTO> findFromToBetween(String stFrom, String stTo, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        Station stationFrom = stationDAO.findByTitle(stFrom);
        Station stationTo = stationDAO.findByTitle(stTo);
        return departureDAO.findFromToBetween(stationFrom, stationTo, dateTimeFrom, dateTimeTo, true)
                .parallelStream().map(departure -> departureToDepartureDTOConverter.convertTo(departure))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartureDTO> findFromToBetween(String stFrom, String stTo, String dateTimeFrom, String dateTimeTo) {
        return findFromToBetween(stFrom, stTo, stringToLocalDateTimeConverter.convertTo(dateTimeFrom), stringToLocalDateTimeConverter.convertTo(dateTimeTo));
    }

    @Transactional(readOnly = true)
    @Override
    public List<DepartureDTO> findAll(boolean fetchStations, boolean fetchTickets) {
        return departureDAO.findAll(fetchStations, fetchTickets, true).parallelStream()
                .map(departure -> departureToDepartureDTOConverter.convertTo(departure))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartureDTO> findAllAvailable(boolean fetchStations, boolean fetchTickets) {
        return departureDAO.findAfterDateTime(LocalDateTime.now(), fetchStations, fetchTickets, true)
                .stream()
                .map(departure -> departureToDepartureDTOConverter.convertTo(departure))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartureDTO> findAllToday() {
        LocalDateTime today = LocalDate.now().toLocalDateTime(LocalTime.MIDNIGHT);
        LocalDateTime tomorrow = today.plusDays(1);
        return departureDAO.findDateTimeFromBetweenOrDateTimeToBetween(today, tomorrow, today, tomorrow, true, false, true)
                .stream()
                .map(departure -> departureToDepartureDTOConverter.convertTo(departure))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartureDTO> findByStationTitle(String stationTitle, boolean fetchStations, boolean fetchTickets) {
        return departureDAO.findByStationTitleFrom(stationTitle, fetchStations, fetchTickets, true).parallelStream()
                .map(departure -> departureToDepartureDTOConverter.convertTo(departure))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Departure save(int coachesCount, String stationFromTitle, String stationToTitle, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        Departure departure = new Departure();
        Station stationFrom = stationDAO.findByTitle(stationFromTitle);
        Station stationTo = stationDAO.findByTitle(stationToTitle);
        if (stationTo == null) {
            throw new NoSuchEntityException("There is no station with title '" + stationToTitle + "'", Station.class);
        }
        if (stationFrom == null) {
            throw new NoSuchEntityException("There is no station with title '" + stationFromTitle + "'", Station.class);
        }
        if (stationFrom.equals(stationTo)) {
            throw new StationEqualsException("You can't add departure from '" + stationFromTitle + "' to '" + stationToTitle + "'");
        }

        stationFrom.getDepartures().add(departure);

        departure.setSitsCount(coachesCount * Coach.DEFAULT_SEATS_NUM);
        departure.setFreeSitsCount(coachesCount * Coach.DEFAULT_SEATS_NUM);
        departure.setStationFrom(stationFrom);
        departure.setStationTo(stationTo);
        departure.setDateTimeFrom(dateTimeFrom);
        departure.setDateTimeTo(dateTimeTo);

        LinkedHashSet<Coach> coaches = new LinkedHashSet<>();
        for (int i = 1; i <= coachesCount; ++i) {
            Coach coach = new Coach();
            coach.setDeparture(departure);
            coach.setCoachNumber(i);
            LinkedHashSet<Ticket> tickets = new LinkedHashSet<>();
            for (int j = 1; j <= Coach.DEFAULT_SEATS_NUM; ++j) {
                Ticket ticket = new Ticket();
                ticket.setCoach(coach);
                ticket.setSiteNum(j);
                tickets.add(ticket);
            }
            coach.setTickets(tickets);
            coaches.add(coach);
        }
        departure.setCoaches(coaches);

        departureDAO.save(departure);

        return departure;
    }

    @Override
    @Transactional
    public Departure save(NewDepartureDTO newDepartureDTO) {
        return save(newDepartureDTO.getCoachCount(), newDepartureDTO.getStationFrom(), newDepartureDTO.getStationTo(),
                newDepartureDTO.getDateTimeFrom(), newDepartureDTO.getDateTimeTo());
    }

    @Override
    public Departure saveWithNotification(int coachesCount, String stationFrom, String stationTo, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        Departure departure = selfProxy.save(coachesCount, stationFrom, stationTo, dateTimeFrom, dateTimeTo);
        rabbitTemplate.convertAndSend(departureToDepartureDTOConverter.convertTo(departure));
        return departure;
    }

    @Override
    public Departure saveWithNotification(NewDepartureDTO newDepartureDTO) {
        Departure departure = selfProxy.save(newDepartureDTO);
        rabbitTemplate.convertAndSend(departureToDepartureDTOConverter.convertTo(departure));
        return departure;
    }
}
