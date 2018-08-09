package javaschool.dao.api;

import javaschool.entity.Passenger;
import javaschool.entity.Ticket;
import javaschool.entity.Trip;

public interface TicketDAO extends GenericDAO<Ticket, Integer> {
    Ticket findByTripIdAndPassenger(Integer tripId, Passenger passenger);
}
