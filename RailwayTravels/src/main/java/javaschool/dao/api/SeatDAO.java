package javaschool.dao.api;

import javaschool.entity.Coach;
import javaschool.entity.Seat;

public interface SeatDAO extends GenericDAO<Seat, Integer> {
    Seat findSeatBySeatNumAndCoach(Integer sitNum, Coach coach);
    Seat findSeatByDepartureAndCoachNumAndSeatNum(Integer departureId, Integer coachNumber, Integer seatNumber);
}
