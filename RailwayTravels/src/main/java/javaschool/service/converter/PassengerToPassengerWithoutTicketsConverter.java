package javaschool.service.converter;

import javaschool.controller.dtoentity.PassengerWithoutTickets;
import javaschool.entity.Passenger;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class PassengerToPassengerWithoutTicketsConverter implements ClassConverter<Passenger, PassengerWithoutTickets> {
    @Override
    public PassengerWithoutTickets convertTo(Passenger passenger) {
        return new PassengerWithoutTickets(passenger.getName(), passenger.getSurname(), passenger.getBirthday().toDate().getTime());
    }

    @Override
    public Passenger convertFrom(PassengerWithoutTickets passengerWithoutTickets) {
        return new Passenger(passengerWithoutTickets.getName(),
                passengerWithoutTickets.getSurname(),
                new LocalDate(passengerWithoutTickets.getBirthday()));
    }
}
