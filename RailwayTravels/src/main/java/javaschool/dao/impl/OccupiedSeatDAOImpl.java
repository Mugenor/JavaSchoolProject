package javaschool.dao.impl;

import java.util.List;
import javaschool.dao.api.OccupiedSeatDAO;
import javaschool.entity.Departure;
import javaschool.entity.Departure_;
import javaschool.entity.OccupiedSeat;
import javaschool.entity.OccupiedSeat_;
import javaschool.entity.Trip;
import javaschool.entity.Trip_;
import javaschool.entity.id.SeatId;
import javaschool.entity.id.SeatId_;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

/**
 * The type Occupied seat dao.
 */
@Repository
public class OccupiedSeatDAOImpl extends GenericAbstractDAO<OccupiedSeat, SeatId> implements OccupiedSeatDAO {

    @Override
    public OccupiedSeat findByDepartureAndSeatNumAndCoachNum(Departure departure, Integer seatNumber, Integer coachNumber) {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<OccupiedSeat> query = builder.createQuery(OccupiedSeat.class);
            Root<OccupiedSeat> occupiedSeatRoot = query.from(OccupiedSeat.class);
            Join<OccupiedSeat, SeatId> seatIdJoin = occupiedSeatRoot.join(OccupiedSeat_.seat);
            query.select(occupiedSeatRoot).where(
                    builder.equal(seatIdJoin.get(SeatId_.departure), departure),
                    builder.equal(seatIdJoin.get(SeatId_.seatNumber), seatNumber),
                    builder.equal(seatIdJoin.get(SeatId_.coachNumber), coachNumber)
            );
            return entityManager.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<OccupiedSeat> findByTripIdAndNumberInTripBounds(Integer tripId, Integer numberInTripFrom, Integer numberInTripTo) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<OccupiedSeat> query = builder.createQuery(OccupiedSeat.class);
        Root<OccupiedSeat> occupiedSeatRoot = query.from(OccupiedSeat.class);
        Join<OccupiedSeat, SeatId> seatIdJoin = occupiedSeatRoot.join(OccupiedSeat_.seat);
        Join<SeatId, Departure> departureJoin = seatIdJoin.join(SeatId_.departure);
        Join<Departure, Trip> tripJoin = departureJoin.join(Departure_.trip);
        query.select(occupiedSeatRoot).where(
                builder.and(
                        builder.equal(tripJoin.get(Trip_.id), tripId),
                        builder.greaterThanOrEqualTo(departureJoin.get(Departure_.numberInTrip), numberInTripFrom),
                        builder.lessThanOrEqualTo(departureJoin.get(Departure_.numberInTrip), numberInTripTo)
                )
        );
        return entityManager.createQuery(query).getResultList();
    }
}
