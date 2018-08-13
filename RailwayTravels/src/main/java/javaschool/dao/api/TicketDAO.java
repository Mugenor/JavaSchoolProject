package javaschool.dao.api;

import java.util.List;
import javaschool.entity.Passenger;
import javaschool.entity.Ticket;
import org.joda.time.LocalDateTime;

public interface TicketDAO extends GenericDAO<Ticket, Integer> {
    Ticket findByTripIdAndPassenger(Integer tripId, Passenger passenger);
    List<Ticket> findByPassengerAndAfter(Passenger passenger, LocalDateTime dateTimeFrom);
    Ticket findByTicketIdAndPassenger(Integer ticketId, Passenger passenger);
    int deleteByTicketIdAndPassenger(Integer ticketId, Passenger passenger);
    long getTicketsCountByTripId(Integer tripId);
}
