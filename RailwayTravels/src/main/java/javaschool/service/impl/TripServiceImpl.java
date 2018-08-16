package javaschool.service.impl;

import java.security.InvalidParameterException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import javaschool.controller.dtoentity.DepartureDTO;
import javaschool.controller.dtoentity.NewDepartureDTO;
import javaschool.controller.dtoentity.OccupiedSeatDTO;
import javaschool.controller.dtoentity.TrainInfo;
import javaschool.controller.dtoentity.TripDTO;
import javaschool.controller.dtoentity.TripInfo;
import javaschool.dao.api.DepartureDAO;
import javaschool.dao.api.OccupiedSeatDAO;
import javaschool.dao.api.TicketDAO;
import javaschool.dao.api.TripDAO;
import javaschool.entity.Departure;
import javaschool.entity.OccupiedSeat;
import javaschool.entity.Trip;
import javaschool.service.api.RabbitService;
import javaschool.service.api.SearchTripsWithTransfers;
import javaschool.service.api.TripService;
import javaschool.service.converter.DepartureToDepartureDTOConverter;
import javaschool.service.converter.DepartureToNewDepartureDTOConverter;
import javaschool.service.converter.TripToTripDTOConverter;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TripServiceImpl implements TripService {
    private TripDAO tripDAO;
    private TicketDAO ticketDAO;
    private DepartureDAO departureDAO;
    private DepartureToDepartureDTOConverter departureToDepartureDTOConverter;
    private TripToTripDTOConverter tripToTripDTOConverter;
    private DepartureToNewDepartureDTOConverter departureToNewDepartureDTOConverter;
    private OccupiedSeatDAO occupiedSeatDAO;
    private RabbitService rabbitService;
    private TripService selfProxy;
    private SearchTripsWithTransfers searchTripsWithTransfers;

    @Autowired
    public TripServiceImpl(TripDAO tripDAO, DepartureDAO departureDAO,
                           DepartureToNewDepartureDTOConverter departureToNewDepartureDTOConverter,
                           TripToTripDTOConverter tripToTripDTOConverter, SearchTripsWithTransfers searchTripsWithTransfers,
                           DepartureToDepartureDTOConverter departureDTOConverter, RabbitService rabbitService,
                           OccupiedSeatDAO occupiedSeatDAO, TicketDAO ticketDAO) {
        this.tripDAO = tripDAO;
        this.departureDAO = departureDAO;
        this.departureToNewDepartureDTOConverter = departureToNewDepartureDTOConverter;
        this.tripToTripDTOConverter = tripToTripDTOConverter;
        this.departureToDepartureDTOConverter = departureDTOConverter;
        this.rabbitService = rabbitService;
        this.searchTripsWithTransfers = searchTripsWithTransfers;
        this.occupiedSeatDAO = occupiedSeatDAO;
        this.ticketDAO = ticketDAO;
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
        LinkedList<TripDTO> resultTrips = new LinkedList<>();
        for (Trip trip : tripsWithArrivalStation) {
            List<Departure> departures = trip.getDepartures();
            ListIterator<Departure> descendingIt = departures.listIterator(departures.size());
            boolean foundEndOfPath = false;
            boolean pathNotFound = true;
            LinkedList<DepartureDTO> path = new LinkedList<>();
            while (descendingIt.hasPrevious()) {
                Departure departure = descendingIt.previous();
                if (departure.getDateTimeFrom().compareTo(dateTimeFrom) < 0) {
                    break;
                }
                if (!foundEndOfPath && departure.getStationTo().getTitle().equals(stTo)
                        && departure.getDateTimeTo().compareTo(dateTimeTo) <= 0
                        && departure.getDateTimeFrom().compareTo(dateTimeFrom) > 0) {
                    foundEndOfPath = true;
                }
                if (foundEndOfPath) {
                    path.addFirst(departureToDepartureDTOConverter.convertTo(departure));
                }
                if (foundEndOfPath && departure.getStationFrom().getTitle().equals(stFrom)) {
                    pathNotFound = false;
                    break;
                }
            }
            if (!pathNotFound) {
                resultTrips.add(new TripDTO(trip.getId(), path, trip.getDepartures().get(0).getCoachCount()));
            }
        }
        return resultTrips;
    }

    @Override
    public List<List<TripDTO>> findFromToBetweenWithTransfers(String stFrom, String stTo,
                                                              LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo,
                                                              int maxTransfersCount) {
        if (stFrom.equals(stTo)) {
            throw new IllegalArgumentException("Stations must be different!");
        } else if (dateTimeFrom.compareTo(dateTimeTo) >= 0) {
            throw new IllegalArgumentException("Departure time must be lower then arrival time!");
        } else if (maxTransfersCount < 0) {
            throw new IllegalArgumentException("Maximum transfer count must not be negative!");
        }
        return searchTripsWithTransfers.findFromToBetweenWithTransfers(stFrom, stTo, dateTimeFrom, dateTimeTo, maxTransfersCount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TripDTO> findAllAvailable() {
        return tripDAO.findAllAfter(LocalDateTime.now())
                .stream()
                .map(trip -> tripToTripDTOConverter.convertTo(trip))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TripDTO> findAllToday() {
        LocalDateTime today = LocalDate.now().toLocalDateTime(LocalTime.MIDNIGHT);
        LocalDateTime tomorrow = today.plusDays(1);
        return tripDAO.findDateTimeFromBetweenBetweenOrDateTimeToBetween(today, tomorrow)
                .stream()
                .map(trip -> tripToTripDTOConverter.convertTo(trip))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TripDTO> findAvailableByStationTitle(String stationTitle) {
        List<Trip> trips = tripDAO.findByStationFromTitleAndDateTimeFromAfter(stationTitle, LocalDateTime.now());
        List<TripDTO> resultList = new LinkedList<>();
        for (Trip trip : trips) {
            List<Departure> departures = trip.getDepartures();
            if (departures.size() == 0) continue;
            int coachCount = departures.get(0).getCoachCount();
            List<DepartureDTO> departureDTOS = new LinkedList<>();
            boolean found = false;
            for (Departure departure : departures) {
                if (!found && departure.getStationFrom().getTitle().equals(stationTitle)) {
                    found = true;
                }
                if (found) {
                    departureDTOS.add(departureToDepartureDTOConverter.convertTo(departure));
                }
            }
            if (departureDTOS.size() == 0) continue;
            resultList.add(new TripDTO(trip.getId(), departureDTOS, coachCount));
        }
        return resultList;
    }

    @Override
    @Transactional(readOnly = true)
    public TrainInfo getDepartureInfoByTripIdAndDepartureBounds(Integer tripId, Integer departureFromIndex, Integer departureToIndex) {
        List<OccupiedSeat> occupiedSeats = occupiedSeatDAO.
                findByTripIdAndNumberInTripBounds(tripId, departureFromIndex, departureToIndex);
        int coachNumber;
        if (occupiedSeats.size() == 0) {
            Trip trip = tripDAO.findById(tripId);
            if (trip != null) {
                coachNumber = trip.getDepartures().get(0).getCoachCount();
            } else {
                throw new IllegalArgumentException("Trip does not exist");
            }
        } else {
            coachNumber = occupiedSeats.get(0).getSeat().getDeparture().getCoachCount();
        }
        LinkedList<OccupiedSeatDTO> occupiedSeatDTOS = new LinkedList<>();
        for (OccupiedSeat seat : occupiedSeats) {
            occupiedSeatDTOS.add(new OccupiedSeatDTO(seat.getSeat().getCoachNumber(), seat.getSeat().getSeatNumber()));
        }
        return new TrainInfo(coachNumber, occupiedSeatDTOS);
    }

    @Override
    public TripInfo getTripInfo(Integer tripId, Integer departureFromIndex, Integer departureToIndex) {
        List<Departure> departures = departureDAO.findByTripIdAndNumberInTripBetween(tripId, departureFromIndex, departureToIndex,
                true, true);
        if (departures.size() == 0) {
            throw new IllegalArgumentException("Trip does not exist");
        }
        Departure first = departures.get(0),
                last = departures.get(departures.size() - 1);
        return new TripInfo(first.getStationFrom().getTitle(), last.getStationTo().getTitle(),
                Instant.parse(first.getDateTimeFrom().toString()).getMillis(),
                Instant.parse(last.getDateTimeTo().toString()).getMillis(),
                first.getCoachCount());
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
    @Transactional
    public TripDTO deleteTrip(Integer tripId) {
        long ticketsCount = ticketDAO.getTicketsCountByTripId(tripId);
        if (ticketsCount != 0) {
            throw new IllegalArgumentException("Passengers already registered on this trip.");
        } else {
            Trip trip = tripDAO.findById(tripId);
            if (trip == null) {
                throw new IllegalArgumentException("Invalid trip");
            } else {
                tripDAO.delete(trip);
            }
            return tripToTripDTOConverter.convertTo(trip);
        }
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
