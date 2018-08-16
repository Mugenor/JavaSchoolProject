package javaschool.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import javaschool.controller.dtoentity.DepartureDTO;
import javaschool.controller.dtoentity.NewDepartureDTO;
import javaschool.dao.api.DepartureDAO;
import javaschool.dao.api.StationDAO;
import javaschool.dao.api.TripDAO;
import javaschool.entity.Departure;
import javaschool.entity.Station;
import javaschool.entity.Trip;
import javaschool.service.api.DepartureService;
import javaschool.service.converter.DepartureToDepartureDTOConverter;
import javaschool.service.converter.DepartureToNewDepartureDTOConverter;
import javaschool.service.exception.NoSuchEntityException;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartureServiceImpl implements DepartureService {
    private DepartureToDepartureDTOConverter departureToDepartureDTOConverter;
    private DepartureToNewDepartureDTOConverter departureToNewDepartureDTOConverter;
    private DepartureDAO departureDAO;
    private TripDAO tripDAO;
    private StationDAO stationDAO;

    @Autowired
    public DepartureServiceImpl(DepartureDAO departureDAO, TripDAO tripDAO,
                                StationDAO stationDAO,
                                DepartureToDepartureDTOConverter departureDTOConverter,
                                DepartureToNewDepartureDTOConverter departureToNewDepartureDTOConverter) {
        this.departureDAO = departureDAO;
        this.stationDAO = stationDAO;
        this.tripDAO = tripDAO;
        this.departureToDepartureDTOConverter = departureDTOConverter;
        this.departureToNewDepartureDTOConverter = departureToNewDepartureDTOConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public DepartureDTO findById(Integer id, boolean fetchStations) {
        return departureToDepartureDTOConverter.convertTo(findByIdRaw(id, fetchStations));
    }

    @Override
    @Transactional(readOnly = true)
    public Departure findByIdRaw(Integer id, boolean fetchStations) {
        Departure departure = departureDAO.findById(id, fetchStations);
        if (departure == null) {
            throw new NoSuchEntityException("Departure is not found!", Departure.class);
        }
        return departure;
    }

    @Transactional(readOnly = true)
    @Override
    public List<DepartureDTO> findAll(boolean fetchStations) {
        return departureDAO.findAll(fetchStations, true).parallelStream()
                .map(departure -> departureToDepartureDTOConverter.convertTo(departure))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Departure save(int coachesCount, String stationFromTitle, String stationToTitle, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        NewDepartureDTO newDepartureDTO = new NewDepartureDTO();
        newDepartureDTO.setCoachCount(coachesCount);
        newDepartureDTO.setStationFrom(stationFromTitle);
        newDepartureDTO.setStationTo(stationToTitle);
        newDepartureDTO.setDateTimeFrom(dateTimeFrom);
        newDepartureDTO.setDateTimeTo(dateTimeTo);
        return save(newDepartureDTO);
    }

    @Override
    @Transactional
    public Departure save(NewDepartureDTO newDepartureDTO) {
        Departure departure = departureToNewDepartureDTOConverter.convertFrom(newDepartureDTO);
        departureDAO.save(departure);
        return departure;
    }

    @Override
    @Transactional
    public void changeDepartureStation(Integer tripId, Integer departureIndex, String newTitle) {
        Trip trip = tripDAO.findById(tripId);
        Station departureStation = stationDAO.findByTitle(newTitle);
        if (trip == null) {
            throw new IllegalArgumentException("Invalid trip");
        }
        if (departureStation == null) {
            throw new IllegalArgumentException("There is no such station");
        }
        for (Departure departure : trip.getDepartures()) {
            if (departure.getNumberInTrip().equals(departureIndex)) {
                if (departure.getStationTo().equals(departureStation)) {
                    throw new IllegalArgumentException("Departure and arrival stations must be different");
                }
                departure.setStationFrom(departureStation);
            }
            if (departure.getNumberInTrip().equals(departureIndex - 1)) {
                if (departure.getStationFrom().equals(departureStation)) {
                    throw new IllegalArgumentException("Departure and arrival stations must be different");
                }
                departure.setStationTo(departureStation);
            }
        }
    }

    @Override
    @Transactional
    public void changeArrivalStation(Integer tripId, Integer departureIndex, String newTitle) {
        Trip trip = tripDAO.findById(tripId);
        Station arrivalStation = stationDAO.findByTitle(newTitle);
        if (trip == null) {
            throw new IllegalArgumentException("Invalid trip");
        }
        if (arrivalStation == null) {
            throw new IllegalArgumentException("There is no such station");
        }
        for (Departure departure : trip.getDepartures()) {
            if (departure.getNumberInTrip().equals(departureIndex)) {
                if (departure.getStationFrom().equals(arrivalStation)) {
                    throw new IllegalArgumentException("Departure and arrival stations must be different");
                }
                departure.setStationTo(arrivalStation);
            }
            if (departure.getNumberInTrip().equals(departureIndex + 1)) {
                if (departure.getStationTo().equals(arrivalStation)) {
                    throw new IllegalArgumentException("Departure and arrival stations must be different");
                }
                departure.setStationFrom(arrivalStation);
            }
        }
    }

    @Override
    @Transactional
    public void changeDepartureTime(Integer tripId, Integer departureIndex, LocalDateTime newTime) {
        Trip trip = tripDAO.findById(tripId);
        if (trip == null) {
            throw new IllegalArgumentException("Invalid trip");
        }
        Departure departureBefore = null, neededDeparture = null;
        for (Departure departure : trip.getDepartures()) {
            if (departure.getNumberInTrip().equals(departureIndex)) {
                neededDeparture = departure;
            }
            if (departure.getNumberInTrip().equals(departureIndex - 1)) {
                departureBefore = departure;
            }
            if (neededDeparture != null && departureBefore != null) {
                break;
            }
        }
        if (neededDeparture == null) {
            throw new IllegalArgumentException("Invalid departure index");
        }
        if (departureBefore != null && departureBefore.getDateTimeTo().compareTo(newTime) >= 0) {
            throw new IllegalArgumentException("Departure time must be after previous arrival time");
        }
        neededDeparture.setDateTimeFrom(newTime);
    }

    @Override
    @Transactional
    public void changeArrivalTime(Integer tripId, Integer departureIndex, LocalDateTime newTime) {
        Trip trip = tripDAO.findById(tripId);
        if (trip == null) {
            throw new IllegalArgumentException("Invalid trip");
        }
        Departure neededDeparture = null, departureAfter = null;
        for (Departure departure : trip.getDepartures()) {
            if (departure.getNumberInTrip().equals(departureIndex)) {
                neededDeparture = departure;
            }
            if (departure.getNumberInTrip().equals(departureIndex + 1)) {
                departureAfter = departure;
            }
            if (departureAfter != null && neededDeparture != null) {
                break;
            }
        }
        if (neededDeparture == null) {
            throw new IllegalArgumentException("Invalid departure index");
        }
        if (departureAfter != null && departureAfter.getDateTimeFrom().compareTo(newTime) <= 0) {
            throw new IllegalArgumentException("Departure time must be after previous arrival time");
        }
        neededDeparture.setDateTimeTo(newTime);
    }
}
