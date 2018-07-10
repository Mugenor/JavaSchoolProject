package javaschool.dao.api;

import javaschool.entity.Coach;
import javaschool.entity.Departure;
import javaschool.entity.Ticket;

public interface TicketDAO extends GenericDAO<Ticket, Integer> {
    Ticket findTicketBySitNumAndCoach(Integer sitNum, Coach coach);
    Ticket findTicketByDepartureAndCoachNumAndSeatNum(Integer departureId, Integer coachNumber, Integer seatNumber);
}
