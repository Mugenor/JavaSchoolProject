package javaschool.service.converter;

import java.util.LinkedList;
import javaschool.controller.dtoentity.DepartureDTO;
import javaschool.controller.dtoentity.TripDTO;
import javaschool.entity.Departure;
import javaschool.entity.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripToTripDTOConverter implements ClassConverter<Trip, TripDTO> {
    private DepartureToDepartureDTOConverter departureToDepartureDTOConverter;

    @Autowired
    public TripToTripDTOConverter(DepartureToDepartureDTOConverter departureToDepartureDTOConverter) {
        this.departureToDepartureDTOConverter = departureToDepartureDTOConverter;
    }

    @Override
    public TripDTO convertTo(Trip trip) {
        TripDTO tripDTO = new TripDTO();
        LinkedList<DepartureDTO> departures = new LinkedList<>();
        tripDTO.setId(trip.getId()).setDepartures(departures);
        for(Departure departure: trip.getDepartures()) {
            departures.addLast(departureToDepartureDTOConverter.convertTo(departure));
        }
        return tripDTO;
    }

    @Override
    public Trip convertFrom(TripDTO tripDTO) {
        Trip trip = new Trip();
        LinkedList<Departure> departures = new LinkedList<>();
        trip.setId(tripDTO.getId()).setDepartures(departures);
        int numberInTrip = Departure.NUMBER_IN_TRIP_OFFSET;
        for(DepartureDTO departureDTO: tripDTO.getDepartures()) {
            departures.addLast(departureToDepartureDTOConverter.convertFrom(departureDTO).setNumberInTrip(numberInTrip++));
        }
        return trip;
    }
}
