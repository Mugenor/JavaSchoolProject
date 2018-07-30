package javaschool.dao.api;

import javaschool.entity.Departure;
import javaschool.entity.Passenger;
import javaschool.entity.PassengerWithTicket;
import javaschool.entity.Ticket;
import javaschool.entity.id.PassengerTicketId;

public interface PassengerWithTicketDAO extends GenericDAO<PassengerWithTicket, PassengerTicketId> {
    void save(Passenger passenger, Ticket ticket, Departure departure);
}
