package javaschool.service.converter;

import java.security.InvalidParameterException;
import java.util.LinkedHashSet;
import javaschool.controller.dtoentity.NewDepartureDTO;
import javaschool.dao.api.StationDAO;
import javaschool.entity.Coach;
import javaschool.entity.Departure;
import javaschool.entity.Seat;
import javaschool.entity.Station;
import javaschool.service.exception.NoSuchEntityException;
import javaschool.service.exception.StationEqualsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartureToNewDepartureDTOConverter implements ClassConverter<Departure, NewDepartureDTO> {
    private StationDAO stationDAO;

    @Autowired
    public DepartureToNewDepartureDTOConverter(StationDAO stationDAO) {
        this.stationDAO = stationDAO;
    }

    @Override
    public NewDepartureDTO convertTo(Departure departure) {
        NewDepartureDTO newDepartureDTO = new NewDepartureDTO();
        newDepartureDTO.setCoachCount(departure.getFreeSitsCount() / Coach.DEFAULT_SEATS_NUM);
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

        departure.setSitsCount(newDepartureDTO.getCoachCount() * Coach.DEFAULT_SEATS_NUM);
        departure.setFreeSitsCount(newDepartureDTO.getCoachCount() * Coach.DEFAULT_SEATS_NUM);
        departure.setStationFrom(stationFrom);
        departure.setStationTo(stationTo);
        departure.setDateTimeFrom(newDepartureDTO.getDateTimeFrom());
        departure.setDateTimeTo(newDepartureDTO.getDateTimeTo());

        LinkedHashSet<Coach> coaches = new LinkedHashSet<>();
        for (int i = 1; i <= newDepartureDTO.getCoachCount(); ++i) {
            Coach coach = new Coach();
            coach.setDeparture(departure);
            coach.setCoachNumber(i);
            LinkedHashSet<Seat> seats = new LinkedHashSet<>();
            for (int j = 1; j <= Coach.DEFAULT_SEATS_NUM; ++j) {
                Seat seat = new Seat();
                seat.setCoach(coach);
                seat.setSiteNum(j);
                seats.add(seat);
            }
            coach.setSeats(seats);
            coaches.add(coach);
        }
        departure.setCoaches(coaches);

        return departure;
    }
}
