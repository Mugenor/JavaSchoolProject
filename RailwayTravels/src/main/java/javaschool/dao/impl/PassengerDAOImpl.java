package javaschool.dao.impl;

import java.util.List;
import javaschool.dao.api.PassengerDAO;
import javaschool.entity.Coach_;
import javaschool.entity.Departure;
import javaschool.entity.Departure_;
import javaschool.entity.OccupiedSeat;
import javaschool.entity.OccupiedSeat_;
import javaschool.entity.Passenger;
import javaschool.entity.Passenger_;
import javaschool.entity.Seat_;
import javaschool.entity.Ticket;
import javaschool.entity.Ticket_;
import javaschool.entity.Trip;
import javaschool.entity.Trip_;
import javaschool.entity.User;
import javaschool.entity.User_;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

@Repository
public class PassengerDAOImpl extends GenericAbstractDAO<Passenger, Integer> implements PassengerDAO {
    private static final Logger log = Logger.getLogger(PassengerDAOImpl.class);
    @Override
    public Passenger findByNameAndSurnameAndBirthday(String name, String surname, LocalDate birthday) {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Passenger> query = builder.createQuery(Passenger.class);
            Root<Passenger> from = query.from(Passenger.class);
            Predicate nameEq = builder.equal(from.get(Passenger_.name), name);
            Predicate surnameEq = builder.equal(from.get(Passenger_.surname), surname);
            Predicate birthdayEq = builder.equal(from.get(Passenger_.birthday), birthday);
            query.where(nameEq, surnameEq, birthdayEq);
            return entityManager.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Passenger> findAllPassengersByDepartureId(Integer departureId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Passenger> query = builder.createQuery(Passenger.class);
        Root<Departure> departureRoot = query.from(Departure.class);
        Join<OccupiedSeat, Ticket> ticketJoin = departureRoot.join(Departure_.coaches)
                .join(Coach_.seats)
                .join(Seat_.occupiedSeat)
                .join(OccupiedSeat_.ticket);
        Join<Ticket, Passenger> passengerJoin = ticketJoin.join(Ticket_.passenger);
        Predicate departureEq = builder.equal(departureRoot.get(Departure_.id), departureId);
        Predicate passengerNotNull = builder.isNotNull(ticketJoin.get(Ticket_.passenger));

        return entityManager.createQuery(query.select(passengerJoin).where(departureEq, passengerNotNull)).getResultList();
    }

    @Override
    public List<Passenger> findAllPassengersByTripIdAndDepartureIndexBounds(Integer tripId, Integer from, Integer to) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Passenger> query = builder.createQuery(Passenger.class);
        Root<Ticket> root = query.from(Ticket.class);
        Join<Ticket, Trip> tripJoin = root.join(Ticket_.trip);
        tripJoin.on(builder.equal(tripJoin.get(Trip_.id), tripId));
        Join<Ticket, Departure> departureFromJoin = root.join(Ticket_.from);
        departureFromJoin.on(builder.greaterThanOrEqualTo(departureFromJoin.get(Departure_.numberInTrip), from));
        Join<Ticket, Departure> departureToJoin = root.join(Ticket_.to);
        departureToJoin.on(builder.lessThanOrEqualTo(departureToJoin.get(Departure_.numberInTrip), to));
        query.select(root.join(Ticket_.passenger));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Passenger> findAllPassengersByTripId(Integer tripId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Passenger> query = builder.createQuery(Passenger.class);
        Root<Trip> tripRoot = query.from(Trip.class);
        Join<OccupiedSeat, Ticket> ticketJoin = tripRoot.join(Trip_.departures)
                .join(Departure_.coaches)
                .join(Coach_.seats)
                .join(Seat_.occupiedSeat)
                .join(OccupiedSeat_.ticket);
        Join<Ticket, Passenger> passengerJoin = ticketJoin.join(Ticket_.passenger);
        Predicate departureEq = builder.equal(tripRoot.get(Trip_.id), tripId);
        Predicate passengerNotNull = builder.isNotNull(ticketJoin.get(Ticket_.passenger));

        return entityManager.createQuery(query.select(passengerJoin).where(departureEq, passengerNotNull)).getResultList();
    }

    @Override
    public Passenger findByUsername(String username) {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Passenger> query = builder.createQuery(Passenger.class);
            Root<Passenger> from = query.from(Passenger.class);
            Join<Passenger, User> userJoin = from.join(Passenger_.user);
            from.fetch(Passenger_.user);
            query.where(builder.equal(userJoin.get(User_.username), username));
            return entityManager.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
