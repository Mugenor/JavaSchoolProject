package javaschool.dao.api;

import javaschool.entity.Coach;
import javaschool.entity.Seat;

public interface TicketDAO extends GenericDAO<Seat, Integer> {
    Seat findTicketBySitNumAndCoach(Integer sitNum, Coach coach);
    Seat findTicketByDepartureAndCoachNumAndSeatNum(Integer departureId, Integer coachNumber, Integer seatNumber);
}
