package javaschool.dao.api;

import java.util.List;
import javaschool.entity.Passenger;
import javaschool.entity.Ticket;
import org.joda.time.LocalDateTime;

/**
 * The interface Ticket dao.
 */
public interface TicketDAO extends GenericDAO<Ticket, Integer> {
    /**
     * Find by trip id and passenger ticket.
     *
     * @param tripId    the trip id
     * @param passenger the passenger
     * @return the ticket
     */
    Ticket findByTripIdAndPassenger(Integer tripId, Passenger passenger);

    /**
     * Find by passenger and after list.
     *
     * @param passenger    the passenger
     * @param dateTimeFrom the date time from
     * @return the list
     */
    List<Ticket> findByPassengerAndAfter(Passenger passenger, LocalDateTime dateTimeFrom);

    /**
     * Find by ticket id and passenger ticket.
     *
     * @param ticketId  the ticket id
     * @param passenger the passenger
     * @return the ticket
     */
    Ticket findByTicketIdAndPassenger(Integer ticketId, Passenger passenger);

    /**
     * Delete by ticket id and passenger int.
     *
     * @param ticketId  the ticket id
     * @param passenger the passenger
     * @return the int
     */
    int deleteByTicketIdAndPassenger(Integer ticketId, Passenger passenger);

    /**
     * Gets tickets count by trip id.
     *
     * @param tripId the trip id
     * @return the tickets count by trip id
     */
    long getTicketsCountByTripId(Integer tripId);
}
