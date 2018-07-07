package javaschool.service.converter;

import javaschool.controller.dtoentity.DepartureDTO;
import javaschool.entity.Departure;
import javaschool.entity.Station;
import javaschool.service.exception.ClassConvertingException;
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
            return new DepartureDTO(departure.getSitsCount(),
                    departure.getFreeSitsCount(),
                    departure.getStationFrom().getTitle(),
                    departure.getStationTo().getTitle(),
                    converter.convertFrom(departure.getDateTimeFrom()),
                    converter.convertFrom(departure.getDateTimeTo()));
        } catch (NullPointerException e) {
            throw new ClassConvertingException(CONVERTING_EXC_MESSAGE, e);
        }
    }

    @Override
    public Departure convertFrom(DepartureDTO departureDTO) {
        Departure departure = new Departure();
        departure.setDateTimeTo(converter.convertTo(departureDTO.getDateTimeTo()));
        departure.setDateTimeFrom(converter.convertTo(departureDTO.getDateTimeFrom()));
        Station station = new Station();
        station.setTitle(departureDTO.getStationTo());
        departure.setStationTo(station);
        station = new Station();
        station.setTitle(departureDTO.getStationFrom());
        departure.setStationFrom(station);
        departure.setFreeSitsCount(departureDTO.getFreeSitsCount());
        departure.setSitsCount(departureDTO.getFreeSitsCount());
        return departure;
    }
}
