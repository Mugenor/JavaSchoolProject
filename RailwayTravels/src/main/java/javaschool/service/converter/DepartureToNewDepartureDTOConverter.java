package javaschool.service.converter;

import java.security.InvalidParameterException;
import javaschool.controller.dtoentity.NewDepartureDTO;
import javaschool.dao.api.StationDAO;
import javaschool.entity.Departure;
import javaschool.entity.Station;
import javaschool.service.exception.NoSuchEntityException;
import javaschool.service.exception.StationEqualsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Departure to new departure dto converter.
 */
@Service
public class DepartureToNewDepartureDTOConverter implements ClassConverter<Departure, NewDepartureDTO> {
    private StationDAO stationDAO;

    /**
     * Instantiates a new Departure to new departure dto converter.
     *
     * @param stationDAO the station dao
     */
    @Autowired
    public DepartureToNewDepartureDTOConverter(StationDAO stationDAO) {
        this.stationDAO = stationDAO;
    }

    @Override
    public NewDepartureDTO convertTo(Departure departure) {
        NewDepartureDTO newDepartureDTO = new NewDepartureDTO();
        newDepartureDTO.setCoachCount(departure.getCoachCount());
        newDepartureDTO.setStationFrom(departure.getStationFrom().getTitle());
        newDepartureDTO.setStationTo(departure.getStationTo().getTitle());
        newDepartureDTO.setDateTimeFrom(departure.getDateTimeFrom());
        newDepartureDTO.setDateTimeTo(departure.getDateTimeTo());
        return newDepartureDTO;
    }

    @Override
    public Departure convertFrom(NewDepartureDTO newDepartureDTO) {
        Departure departure = new Departure();
        Station stationFrom = stationDAO.findByTitle(newDepartureDTO.getStationFrom());
        Station stationTo = stationDAO.findByTitle(newDepartureDTO.getStationTo());
        if (stationTo == null) {
            throw new NoSuchEntityException("There is no station with title '" + newDepartureDTO.getStationTo() + "'", Station.class);
        }
        if (stationFrom == null) {
            throw new NoSuchEntityException("There is no station with title '" + newDepartureDTO.getStationFrom() + "'", Station.class);
        }
        if (stationFrom.equals(stationTo)) {
            throw new StationEqualsException("You can't add departure from '" + newDepartureDTO.getStationFrom() + "' to '"
                    + newDepartureDTO.getStationTo() + "'");
        }
        if(newDepartureDTO.getCoachCount() <= 0) {
            throw new InvalidParameterException("Coach number must be positive!");
        }
        if(newDepartureDTO.getDateTimeFrom().compareTo(newDepartureDTO.getDateTimeTo()) >= 0) {
            throw new InvalidParameterException("Departure time must be less then arrival time!");
        }

        stationFrom.getDepartures().add(departure);

        departure.setFreeSitsCount(newDepartureDTO.getCoachCount() * Departure.DEFAULT_SEATS_COUNT_IN_COACH);
        departure.setCoachCount(newDepartureDTO.getCoachCount());
        departure.setSeatInCoach(Departure.DEFAULT_SEATS_COUNT_IN_COACH);
        departure.setStationFrom(stationFrom);
        departure.setStationTo(stationTo);
        departure.setDateTimeFrom(newDepartureDTO.getDateTimeFrom());
        departure.setDateTimeTo(newDepartureDTO.getDateTimeTo());

        return departure;
    }
}
