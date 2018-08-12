package javaschool.dao.api;

import java.util.List;
import javaschool.entity.Departure;
import javaschool.entity.OccupiedSeat;
import javaschool.entity.id.SeatId;

public interface OccupiedSeatDAO extends GenericDAO<OccupiedSeat, SeatId> {
    OccupiedSeat findByDepartureAndSeatNumAndCoachNum(Departure departure, Integer seatNumber, Integer coachNumber);
    List<OccupiedSeat> findByTripIdAndNumberInTripBounds(Integer tripId, Integer numberInTripFrom, Integer numberInTripTo);
}
