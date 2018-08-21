package javaschool.dao.api;

import java.util.List;
import javaschool.entity.Departure;
import javaschool.entity.OccupiedSeat;
import javaschool.entity.id.SeatId;

/**
 * The interface Occupied seat dao.
 */
public interface OccupiedSeatDAO extends GenericDAO<OccupiedSeat, SeatId> {
    /**
     * Find by departure and seat num and coach num occupied seat.
     *
     * @param departure   the departure
     * @param seatNumber  the seat number
     * @param coachNumber the coach number
     * @return the occupied seat
     */
    OccupiedSeat findByDepartureAndSeatNumAndCoachNum(Departure departure, Integer seatNumber, Integer coachNumber);

    /**
     * Find by trip id and number in trip bounds list.
     *
     * @param tripId           the trip id
     * @param numberInTripFrom the number in trip from
     * @param numberInTripTo   the number in trip to
     * @return the list
     */
    List<OccupiedSeat> findByTripIdAndNumberInTripBounds(Integer tripId, Integer numberInTripFrom, Integer numberInTripTo);
}
