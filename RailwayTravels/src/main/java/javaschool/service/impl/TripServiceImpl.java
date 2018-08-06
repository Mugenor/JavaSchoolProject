package javaschool.service.impl;

import java.security.InvalidParameterException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import javaschool.controller.dtoentity.DepartureDTO;
import javaschool.controller.dtoentity.NewDepartureDTO;
import javaschool.controller.dtoentity.TripDTO;
import javaschool.dao.api.DepartureDAO;
import javaschool.dao.api.TripDAO;
import javaschool.entity.Departure;
import javaschool.entity.Trip;
import javaschool.service.api.RabbitService;
import javaschool.service.api.TripService;
import javaschool.service.converter.DepartureToDepartureDTOConverter;
import javaschool.service.converter.DepartureToNewDepartureDTOConverter;
import javaschool.service.converter.TripToTripDTOConverter;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TripServiceImpl implements TripService {
    private TripDAO tripDAO;
    private DepartureDAO departureDAO;
    private DepartureToDepartureDTOConverter departureToDepartureDTOConverter;
    private TripToTripDTOConverter tripToTripDTOConverter;
    private DepartureToNewDepartureDTOConverter departureToNewDepartureDTOConverter;
    private RabbitService rabbitService;
    private TripService selfProxy;

    @Autowired
    public TripServiceImpl(TripDAO tripDAO, DepartureDAO departureDAO,
                           DepartureToNewDepartureDTOConverter departureToNewDepartureDTOConverter,
                           TripToTripDTOConverter tripToTripDTOConverter,
                           DepartureToDepartureDTOConverter departureDTOConverter, RabbitService rabbitService) {
        this.tripDAO = tripDAO;
        this.departureDAO = departureDAO;
        this.departureToNewDepartureDTOConverter = departureToNewDepartureDTOConverter;
        this.tripToTripDTOConverter = tripToTripDTOConverter;
        this.departureToDepartureDTOConverter = departureDTOConverter;
        this.rabbitService = rabbitService;
    }

    @Autowired
    public TripServiceImpl setSelfProxy(TripService selfProxy) {
        this.selfProxy = selfProxy;
        return this;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TripDTO> findAll() {
        return tripDAO.findAll().stream()
                .map(trip -> tripToTripDTOConverter.convertTo(trip))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TripDTO> findFromToBetween(String stFrom, String stTo, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        List<Trip> tripsWithArrivalStation = tripDAO.findByStationTitleToDateTimeToBetween(stTo, dateTimeFrom, dateTimeTo);
        List<Trip> tripsWithDepartureStation = tripDAO.findByStationTitleFromDateTimeFromBetween(stFrom, dateTimeFrom, dateTimeTo);
        tripsWithArrivalStation.retainAll(tripsWithDepartureStation);
        LinkedList<TripDTO> resultTrips = new LinkedList<>();
        for (Trip trip : tripsWithArrivalStation) {
            List<Departure> departures = trip.getDepartures();
            ListIterator<Departure> descendingIt = departures.listIterator(departures.size());
            boolean foundEndOfPath = false;
            boolean pathNotFound = true;
            LinkedList<DepartureDTO> path = new LinkedList<>();
            while (descendingIt.hasPrevious()) {
                Departure departure = descendingIt.previous();
                if(departure.getDateTimeFrom().compareTo(dateTimeFrom) < 0) {
                    break;
                }
                if(!foundEndOfPath && departure.getStationTo().getTitle().equals(stTo)
                        && departure.getDateTimeTo().compareTo(dateTimeTo) <= 0
                        && departure.getDateTimeFrom().compareTo(dateTimeFrom) > 0) {
                    foundEndOfPath = true;
                }
                if(foundEndOfPath) {
                    path.addFirst(departureToDepartureDTOConverter.convertTo(departure));
                }
                if(foundEndOfPath && departure.getStationFrom().getTitle().equals(stFrom)) {
                    pathNotFound = false;
                    break;
                }
            }
            if(!pathNotFound) {
                resultTrips.add(new TripDTO(trip.getId(), path));
            }
        }
        return resultTrips;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TripDTO> findAllToday() {
        return tripDAO.findAllAfter(LocalDate.now().toLocalDateTime(LocalTime.MIDNIGHT))
                .stream()
                .map(trip -> tripToTripDTOConverter.convertTo(trip))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TripDTO> findAllAvailable() {
        LocalDateTime today = LocalDate.now().toLocalDateTime(LocalTime.MIDNIGHT);
        LocalDateTime tomorrow = today.plusDays(1);
        return tripDAO.findDateTimeFromBetweenBetweenOrDateTimeToBetween(today, tomorrow)
                .stream()
                .map(trip -> tripToTripDTOConverter.convertTo(trip))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TripDTO> findByStationTitle(String stationTitle) {
        return tripDAO.findByDepartures(
                departureDAO.findByStationTitleFrom(stationTitle, false, false, true))
                .stream()
                .map(trip -> tripToTripDTOConverter.convertTo(trip))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TripDTO save(List<NewDepartureDTO> departureDTOS) {
        if (departureDTOS == null || departureDTOS.size() == 0) {
            throw new InvalidParameterException("Departure list must contain at least one departure!");
        }
        int numInTrip = Departure.NUMBER_IN_TRIP_OFFSET;
        Trip trip = new Trip();
        LinkedList<Departure> departures = new LinkedList<>();
        trip.setDepartures(departures);
        Iterator<NewDepartureDTO> departureIt = departureDTOS.iterator();
        NewDepartureDTO curDepartureDTO = departureIt.next();
        String lastStationTo = curDepartureDTO.getStationTo();
        LocalDateTime lastDateTimeTo = curDepartureDTO.getDateTimeTo();
        departures.addLast(departureToNewDepartureDTOConverter.convertFrom(curDepartureDTO)
                .setNumberInTrip(numInTrip++).setTrip(trip));

        while (departureIt.hasNext()) {
            curDepartureDTO = departureIt.next();
            validateNewDepartureDTO(curDepartureDTO, lastStationTo, lastDateTimeTo);
            lastStationTo = curDepartureDTO.getStationTo();
            lastDateTimeTo = curDepartureDTO.getDateTimeTo();
            departures.add(departureToNewDepartureDTOConverter.convertFrom(curDepartureDTO)
                    .setNumberInTrip(numInTrip++).setTrip(trip));
        }
        tripDAO.save(trip);
        return tripToTripDTOConverter.convertTo(trip);
    }

    @Override
    public TripDTO saveWithNotification(List<NewDepartureDTO> departures) {
        TripDTO tripDTO = selfProxy.save(departures);
        rabbitService.convertAndSend(tripDTO);
        return tripDTO;
    }

    private void validateNewDepartureDTO(NewDepartureDTO newDepartureDTO, String lastStationTo, LocalDateTime lastDateTimeTo) {
        if (!lastStationTo.equals(newDepartureDTO.getStationFrom())) {
            throw new InvalidParameterException("Arrival station and departure station of sequential departures must be the same!");
        }
        if (lastDateTimeTo.compareTo(newDepartureDTO.getDateTimeFrom()) >= 0) {
            throw new InvalidParameterException("Arrival time must be less then departure time of sequential departures!");
        }

    }
}
