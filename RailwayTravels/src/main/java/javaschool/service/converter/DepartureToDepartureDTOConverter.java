package javaschool.service.converter;

import javaschool.controller.dtoentity.DepartureDTO;
import javaschool.entity.Coach;
import javaschool.entity.Departure;
import javaschool.entity.Station;
import javaschool.service.exception.ClassConvertingException;
import org.joda.time.Instant;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartureToDepartureDTOConverter implements ClassConverter<Departure, DepartureDTO> {
    private static final String CONVERTING_EXC_MESSAGE = "Station is not found for this departure";

    private StringToLocalDateTimeConverter converter;

    @Autowired
    public DepartureToDepartureDTOConverter(StringToLocalDateTimeConverter converter) {
        this.converter = converter;
    }

    @Override
    public DepartureDTO convertTo(Departure departure) {
        try {
            return new DepartureDTO(departure.getId(),
                    departure.getSitsCount(),
                    departure.getFreeSitsCount(),
                    departure.getStationFrom().getTitle(),
                    departure.getStationTo().getTitle(),
                    Instant.parse(departure.getDateTimeFrom().toString()).getMillis(),
                    Instant.parse(departure.getDateTimeTo().toString()).getMillis(),
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
        departure.setSitsCount(departureDTO.getFreeSitsCount());
        departure.setNumberInTrip(departureDTO.getNumberInTrip());
        return departure;
    }
}
