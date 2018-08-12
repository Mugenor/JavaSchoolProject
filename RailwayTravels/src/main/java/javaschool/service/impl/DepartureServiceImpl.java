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
import javaschool.service.converter.StringToLocalDateTimeConverter;
import javaschool.service.exception.NoSuchEntityException;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartureServiceImpl implements DepartureService {
    private StringToLocalDateTimeConverter stringToLocalDateTimeConverter;
    private DepartureToDepartureDTOConverter departureToDepartureDTOConverter;
    private DepartureToNewDepartureDTOConverter departureToNewDepartureDTOConverter;
    private DepartureDAO departureDAO;
    private StationDAO stationDAO;

    @Autowired
    public DepartureServiceImpl(DepartureDAO departureDAO, StationDAO stationDAO,
                                StringToLocalDateTimeConverter converter, DepartureToDepartureDTOConverter departureDTOConverter,
                                DepartureToNewDepartureDTOConverter departureToNewDepartureDTOConverter) {
        this.stationDAO = stationDAO;
        this.departureDAO = departureDAO;
        this.stringToLocalDateTimeConverter = converter;
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
    public List<DepartureDTO> findAll(boolean fetchStations) {
        return departureDAO.findAll(fetchStations, true).parallelStream()
                .map(departure -> departureToDepartureDTOConverter.convertTo(departure))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartureDTO> findAllAvailable(boolean fetchStations) {
        return departureDAO.findAfterDateTime(LocalDateTime.now(), fetchStations, true)
                .stream()
                .map(departure -> departureToDepartureDTOConverter.convertTo(departure))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartureDTO> findAllToday() {
        LocalDateTime today = LocalDate.now().toLocalDateTime(LocalTime.MIDNIGHT);
        LocalDateTime tomorrow = today.plusDays(1);
        return departureDAO.findDateTimeFromBetweenOrDateTimeToBetween(today, tomorrow, today, tomorrow, true, true)
                .stream()
                .map(departure -> departureToDepartureDTOConverter.convertTo(departure))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartureDTO> findByStationTitle(String stationTitle, boolean fetchStations) {
        return departureDAO.findByStationTitleFrom(stationTitle, fetchStations, true).parallelStream()
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
}
