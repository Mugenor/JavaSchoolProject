package javaschool.dao.impl;

import javaschool.dao.api.SeatDAO;
import javaschool.entity.Coach;
import javaschool.entity.Coach_;
import javaschool.entity.Departure;
import javaschool.entity.Departure_;
import javaschool.entity.Seat;
import javaschool.entity.Seat_;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
}
