package javaschool.dao.api;

import java.util.List;
import javaschool.entity.Coach;
import javaschool.entity.Seat;

public interface SeatDAO extends GenericDAO<Seat, Integer> {
    Seat findSeatBySeatNumAndCoach(Integer sitNum, Coach coach);
    List<Seat> findByTripIdAndDepartureBounds(Integer tripId, Integer departureFromIndex, Integer departureToIndex);
    Seat findSeatByDepartureAndCoachNumAndSeatNum(Integer departureId, Integer coachNumber, Integer seatNumber);
}
