package javaschool.dao.impl;

import java.util.List;
import javaschool.dao.api.CoachDAO;
import javaschool.entity.Coach;
import javaschool.entity.Coach_;
import javaschool.entity.Departure;
import javaschool.entity.Departure_;
import javaschool.entity.OccupiedSeat_;
import javaschool.entity.Seat;
import javaschool.entity.Seat_;
import javaschool.entity.Ticket;
import javaschool.entity.Ticket_;
import javaschool.entity.Trip;
import javaschool.entity.Trip_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import org.springframework.stereotype.Repository;

@Repository
public class CoachDAOImpl extends GenericAbstractDAO<Coach, Integer> implements CoachDAO {
    @Override
    public List<Coach> findOccupiedSeatsByTripIdAndDepartureBounds(Integer tripId, Integer departureFromIndex, Integer departureToIndex) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Coach> query = builder.createQuery(Coach.class);
        Root<Ticket> ticketRoot = query.from(Ticket.class);
        Join<Seat, Coach> coachJoin = ticketRoot.join(Ticket_.occupiedSeats).join(OccupiedSeat_.seat)
                .join(Seat_.coach);
        coachJoin.fetch(Coach_.seats);
        query.select(coachJoin).where(
                builder.and(
                        builder.equal(ticketRoot.join(Ticket_.trip).get(Trip_.id), tripId),
                        builder.greaterThanOrEqualTo(ticketRoot.join(Ticket_.from).get(Departure_.numberInTrip), departureFromIndex),
                        builder.lessThanOrEqualTo(ticketRoot.join(Ticket_.to).get(Departure_.numberInTrip), departureToIndex)
                )
        );
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Coach> findByTripIdAndDepartureBounds(Integer tripId, Integer departureFromIndex, Integer departureToIndex) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Coach> query = builder.createQuery(Coach.class);
        Root<Trip> tripRoot = query.from(Trip.class);
        ListJoin<Trip, Departure> departureJoin = tripRoot.join(Trip_.departures);
        Predicate tripIdEq = builder.equal(tripRoot.get(Trip_.id), tripId);
        Predicate boundsPredicate = builder.and(
                builder.greaterThanOrEqualTo(departureJoin.get(Departure_.numberInTrip), departureFromIndex),
                builder.lessThanOrEqualTo(departureJoin.get(Departure_.numberInTrip), departureToIndex)
        );
        SetJoin<Departure, Coach> coachJoin = departureJoin.join(Departure_.coaches);
        coachJoin.fetch(Coach_.seats).fetch(Seat_.occupiedSeat);
        query.select(coachJoin).where(builder.and(
                tripIdEq,
                boundsPredicate
        ));
        return entityManager.createQuery(query).getResultList();
    }
}
