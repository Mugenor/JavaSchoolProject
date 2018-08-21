package javaschool.service.converter;

import java.util.LinkedList;
import javaschool.controller.dtoentity.DepartureDTO;
import javaschool.controller.dtoentity.TripDTO;
import javaschool.entity.Departure;
import javaschool.entity.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Trip to trip dto converter.
 */
@Service
public class TripToTripDTOConverter implements ClassConverter<Trip, TripDTO> {
    private DepartureToDepartureDTOConverter departureToDepartureDTOConverter;

    private TripToTripDTOConverter() {}

    /**
     * Instantiates a new Trip to trip dto converter.
     *
     * @param departureToDepartureDTOConverter the departure to departure dto converter
     */
    @Autowired
    public TripToTripDTOConverter(DepartureToDepartureDTOConverter departureToDepartureDTOConverter) {
        this.departureToDepartureDTOConverter = departureToDepartureDTOConverter;
    }

    @Override
    public TripDTO convertTo(Trip trip) {
        TripDTO tripDTO = new TripDTO();
        convertTrip(trip, tripDTO);
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

    /**
     * Convert trip.
     *
     * @param trip the trip
     * @param res  the res
     */
    protected void convertTrip(Trip trip, TripDTO res) {
        res.setCoachCount(trip.getDepartures().get(0).getCoachCount());
        LinkedList<DepartureDTO> departures = new LinkedList<>();
        res.setId(trip.getId()).setDepartures(departures);
        for(Departure departure: trip.getDepartures()) {
            departures.addLast(departureToDepartureDTOConverter.convertTo(departure));
        }
    }
}
