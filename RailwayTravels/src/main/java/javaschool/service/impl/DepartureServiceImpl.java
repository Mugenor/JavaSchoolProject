package javaschool.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import javaschool.controller.dtoentity.DepartureDTO;
import javaschool.controller.dtoentity.NewDepartureDTO;
import javaschool.dao.api.DepartureDAO;
import javaschool.dao.api.StationDAO;
import javaschool.entity.Departure;
import javaschool.entity.Station;
import javaschool.service.api.DepartureService;
import javaschool.service.converter.DepartureToDepartureDTOConverter;
import javaschool.service.converter.DepartureToNewDepartureDTOConverter;
import javaschool.service.exception.NoSuchEntityException;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Departure service.
 */
@Service
public class DepartureServiceImpl implements DepartureService {
    private DepartureToDepartureDTOConverter departureToDepartureDTOConverter;
    private DepartureToNewDepartureDTOConverter departureToNewDepartureDTOConverter;
    private DepartureDAO departureDAO;
    private StationDAO stationDAO;

    /**
     * Instantiates a new Departure service.
     *
     * @param departureDAO                        the departure dao
     * @param stationDAO                          the station dao
     * @param departureDTOConverter               the departure dto converter
     * @param departureToNewDepartureDTOConverter the departure to new departure dto converter
     */
    @Autowired
    public DepartureServiceImpl(DepartureDAO departureDAO,
                                StationDAO stationDAO,
                                DepartureToDepartureDTOConverter departureDTOConverter,
                                DepartureToNewDepartureDTOConverter departureToNewDepartureDTOConverter) {
        this.departureDAO = departureDAO;
        this.stationDAO = stationDAO;
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
    public void updateDeparture(Integer tripId, Integer departureIndex, NewDepartureDTO departureDTO) {
        List<Departure> departures = departureDAO.findByTripIdAndNumberInTripBetween(tripId, departureIndex - 1, departureIndex + 1,
                true, true);
        if (departures == null || departures.isEmpty()) {
            throw new IllegalArgumentException("Invalid query");
        }
        Departure departure = null;
        Departure departureBefore = null;
        Departure departureAfter = null;
        for (Departure curDep : departures) {
            if (departureIndex.equals(curDep.getNumberInTrip())) {
                departure = curDep;
            } else if (departureIndex > curDep.getNumberInTrip()) {
                departureBefore = curDep;
            } else {
                departureAfter = curDep;
            }
        }
        if (departure == null) {
            throw new IllegalArgumentException("Invalid query");
        }
        if (departureDTO.getStationFrom().equals(departureDTO.getStationTo())) {
            throw new IllegalArgumentException("Departure station and arrival station must be different");
        }
        if (departureDTO.getDateTimeFrom().compareTo(departureDTO.getDateTimeTo()) >= 0) {
            throw new IllegalArgumentException("Departure time must be before arrival time");
        }
        updateStationFrom(departureDTO, departure, departureBefore);
        updateStationTo(departureDTO, departure, departureAfter);
        updateDateTimeFrom(departureDTO, departure, departureBefore);
        updateDateTimeTo(departureDTO, departure, departureAfter);
    }

    private void updateStationFrom(NewDepartureDTO updateDeparture, Departure curDeparture, Departure departureBefore) {
        Station departureStation = stationDAO.findByTitle(updateDeparture.getStationFrom());
        if (departureStation == null) {
            throw new IllegalArgumentException("Invalid station name");
        }
        if (departureBefore != null) {
            if (departureBefore.getStationFrom().getId().equals(departureStation.getId())) {
                throw new IllegalArgumentException("Departure station and arrival station must be different");
            }
            departureBefore.setStationTo(departureStation);
        }
        curDeparture.setStationFrom(departureStation);
    }

    private void updateStationTo(NewDepartureDTO updateDeparture, Departure curDeparture, Departure departureAfter) {
        Station arrivalStation = stationDAO.findByTitle(updateDeparture.getStationTo());
        if (arrivalStation == null) {
            throw new IllegalArgumentException("Invalid station name");
        }
        if (departureAfter != null) {
            if (departureAfter.getStationTo().getId().equals(arrivalStation.getId())) {
                throw new IllegalArgumentException("Departure station and arrival station must be different");
            }
            departureAfter.setStationFrom(arrivalStation);
        }
        curDeparture.setStationTo(arrivalStation);
    }

    private void updateDateTimeFrom(NewDepartureDTO updateDeparture, Departure curDeparture, Departure departureBefore) {
        if (departureBefore != null && updateDeparture.getDateTimeFrom().compareTo(departureBefore.getDateTimeTo()) <= 0) {
            throw new IllegalArgumentException("Departure time must be before arrival time");
        }
        curDeparture.setDateTimeFrom(updateDeparture.getDateTimeFrom());
    }

    private void updateDateTimeTo(NewDepartureDTO updateDeparture, Departure curDeparture, Departure departureAfter) {
        if (departureAfter != null && updateDeparture.getDateTimeTo().compareTo(departureAfter.getDateTimeFrom()) >= 0) {
            throw new IllegalArgumentException("Departure time must be before arrival time");
        }
        curDeparture.setDateTimeTo(updateDeparture.getDateTimeTo());
    }


}
