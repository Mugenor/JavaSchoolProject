package javaschool.dao.impl;

import java.util.List;
import javaschool.dao.api.SeatDAO;
import javaschool.entity.Coach;
import javaschool.entity.Coach_;
import javaschool.entity.Departure;
import javaschool.entity.Departure_;
import javaschool.entity.OccupiedSeat;
import javaschool.entity.OccupiedSeat_;
import javaschool.entity.Seat;
import javaschool.entity.Seat_;
import javaschool.entity.Ticket;
import javaschool.entity.Ticket_;
import javaschool.entity.Trip_;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import org.springframework.stereotype.Repository;

@Repository
public class SeatDAOImpl extends GenericAbstractDAO<Seat, Integer> implements SeatDAO {
    @Override
    public Seat findSeatBySeatNumAndCoach(Integer siteNum, Coach coach) {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Seat> query = builder.createQuery(Seat.class);
            Root<Seat> from = query.from(Seat.class);
            Predicate siteNumEq = builder.equal(from.get(Seat_.siteNum), siteNum);
            Predicate coachEq = builder.equal(from.get(Seat_.coach), coach);
            return entityManager.createQuery(query.where(coachEq, siteNumEq)).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Seat findSeatByDepartureAndCoachNumAndSeatNum(Integer departureId, Integer coachNumber, Integer seatNumber) {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Seat> query = builder.createQuery(Seat.class);
            Root<Departure> from = query.from(Departure.class);
            SetJoin<Departure, Coach> coachSetJoin = from.join(Departure_.coaches);
            SetJoin<Coach, Seat> ticketSetJoin = coachSetJoin.join(Coach_.seats);
            Predicate coachNumberEq = builder.equal(coachSetJoin.get(Coach_.coachNumber), coachNumber);
            Predicate seatNumberEq = builder.equal(ticketSetJoin.get(Seat_.siteNum), seatNumber);
            Predicate departureEq = builder.equal(from.get(Departure_.id), departureId);
            query.select(ticketSetJoin).where(builder.and(coachNumberEq, seatNumberEq, departureEq));

            return entityManager.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Seat> findByTripIdAndDepartureBounds(Integer tripId, Integer departureFromIndex, Integer departureToIndex) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Seat> query = builder.createQuery(Seat.class);
        Root<Ticket> ticketRoot = query.from(Ticket.class);
        Join<OccupiedSeat, Seat> seatJoin = ticketRoot.join(Ticket_.occupiedSeats).join(OccupiedSeat_.seat);
        Join<Ticket, Departure> departureFromJoin = ticketRoot.join(Ticket_.from);
        Join<Ticket, Departure> departureToJoin = ticketRoot.join(Ticket_.to);
        query.select(seatJoin).where(
                builder.and(
                        builder.equal(ticketRoot.join(Ticket_.trip).get(Trip_.id), tripId),
                        builder.or(
                                builder.and(
                                        builder.greaterThanOrEqualTo(departureFromJoin.get(Departure_.numberInTrip), departureFromIndex),
                                        builder.lessThanOrEqualTo(departureFromJoin.get(Departure_.numberInTrip), departureToIndex)
                                ),
                                builder.and(
                                        builder.greaterThanOrEqualTo(departureToJoin.get(Departure_.numberInTrip), departureFromIndex),
                                        builder.lessThanOrEqualTo(departureToJoin.get(Departure_.numberInTrip), departureToIndex)
                                ),
                                builder.and(
                                        builder.lessThan(departureFromJoin.get(Departure_.numberInTrip), departureFromIndex),
                                        builder.greaterThan(departureToJoin.get(Departure_.numberInTrip), departureToIndex)
                                )
                        )
                )
        );
        seatJoin.fetch(Seat_.coach);
        return entityManager.createQuery(query).getResultList();
    }
}
