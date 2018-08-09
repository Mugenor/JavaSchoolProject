package javaschool.dao.api;

import java.util.List;
import javaschool.entity.Coach;

public interface CoachDAO extends GenericDAO<Coach, Integer> {
    List<Coach> findOccupiedSeatsByTripIdAndDepartureBounds(Integer tripId, Integer departureFromIndex, Integer departureToIndex);
    List<Coach> findByTripIdAndDepartureBounds(Integer tripId, Integer departureFromIndex, Integer departureToIndex);
}
