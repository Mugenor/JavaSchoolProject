package javaschool.dao.api;

import javaschool.entity.Departure;
import javaschool.entity.Ticket;

public interface TicketDAO extends GenericDAO<Ticket, Integer> {
    Ticket findTicketBySitNumAndDeparture(Integer sitNum, Departure departure);
}
