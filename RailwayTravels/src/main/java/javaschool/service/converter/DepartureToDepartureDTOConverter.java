package javaschool.service.converter;

import javaschool.controller.dtoentity.DepartureDTO;
import javaschool.entity.Departure;
import javaschool.entity.Station;
import javaschool.service.exception.ClassConvertingException;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class DepartureToDepartureDTOConverter implements ClassConverter<Departure, DepartureDTO> {
    private static final String CONVERTING_EXC_MESSAGE = "Station is not found for this departure";

    @Override
    public DepartureDTO convertTo(Departure departure) {
        try {
            return new DepartureDTO(departure.getId(),
                    departure.getSeatInCoach() * departure.getCoachCount(),
                    departure.getFreeSitsCount(),
                    departure.getStationFrom().getTitle(),
                    departure.getStationTo().getTitle(),
                    departure.getDateTimeFrom().toDateTime().getMillis(),
                    departure.getDateTimeTo().toDateTime().getMillis(),
                    departure.getNumberInTrip());
        } catch (NullPointerException e) {
            throw new ClassConvertingException(CONVERTING_EXC_MESSAGE, e);
        }
    }

    @Override
    public Departure convertFrom(DepartureDTO departureDTO) {
        Departure departure = new Departure();
        departure.setId(departureDTO.getId());
        departure.setDateTimeTo(new LocalDateTime(departureDTO.getDateTimeTo()));
        departure.setDateTimeFrom(new LocalDateTime(departureDTO.getDateTimeFrom()));
        departure.setStationTo(new Station(departureDTO.getStationTo()));
        departure.setStationFrom(new Station(departureDTO.getStationFrom()));
        departure.setFreeSitsCount(departureDTO.getFreeSitsCount());
        departure.setSeatInCoach(Departure.DEFAULT_SEATS_COUNT_IN_COACH);
        departure.setCoachCount(departureDTO.getSitsCount() / Departure.DEFAULT_SEATS_COUNT_IN_COACH);
        departure.setNumberInTrip(departureDTO.getNumberInTrip());
        return departure;
    }
}
