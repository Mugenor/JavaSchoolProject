package javaschool.dao.impl;

import java.util.List;
import javaschool.dao.api.DepartureDAO;
import javaschool.entity.Coach;
import javaschool.entity.Coach_;
import javaschool.entity.Departure;
import javaschool.entity.Departure_;
import javaschool.entity.Station;
import javaschool.entity.Station_;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Repository;

@Repository
public class DepartureDAOImpl extends GenericAbstractDAO<Departure, Integer> implements DepartureDAO {

    @Override
    public List<Departure> findByStationTitleFrom(String stationTitle, boolean fetchStations, boolean fetchTickets,
                                                  boolean orderByDateTimeFrom) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Departure> query = builder.createQuery(Departure.class);
        Root<Departure> from = query.from(Departure.class);
        Join<Departure, Station> stationFromJoin = from.join(Departure_.stationFrom);
        Predicate stationTitleEq = builder.equal(stationFromJoin.get(Station_.title), stationTitle);

        findDeparture(builder, query, from, fetchStations, fetchTickets, orderByDateTimeFrom);

        query.select(from).where(stationTitleEq);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Departure> findFromToBetween(Station stFrom, Station stTo, LocalDateTime dateFrom, LocalDateTime dateTo,
                                             boolean orderByDateTimeFrom) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Departure> query = builder.createQuery(Departure.class);
        Root<Departure> from = query.from(Departure.class);
        Predicate equalStationFrom = builder.equal(from.get(Departure_.stationFrom), stFrom);
        Predicate equalStationTo = builder.equal(from.get(Departure_.stationTo), stTo);
        Predicate greaterThanLeftDate = builder.greaterThan(from.get(Departure_.dateTimeFrom), dateFrom);
        Predicate lessThanRightDate = builder.lessThan(from.get(Departure_.dateTimeFrom), dateTo);
        Predicate resultPredicate = builder.and(equalStationFrom, equalStationTo, greaterThanLeftDate, lessThanRightDate);
        query.where(resultPredicate);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Departure findByStFromAndStToAndDateFromAndDateTo(Station stFrom, Station stTo, LocalDateTime dateFrom, LocalDateTime dateTo) {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Departure> query = builder.createQuery(Departure.class);
            Root<Departure> from = query.from(Departure.class);
            Predicate equalStationFrom = builder.equal(from.get(Departure_.stationFrom), stFrom);
            Predicate equalStationTo = builder.equal(from.get(Departure_.stationTo), stTo);
            Predicate equalLeftDate = builder.equal(from.get(Departure_.dateTimeFrom), dateFrom);
            Predicate equalRightDate = builder.equal(from.get(Departure_.dateTimeTo), dateTo);
            Predicate resultPredicate = builder.and(equalStationFrom, equalStationTo, equalLeftDate, equalRightDate);
            query.where(resultPredicate);

            return entityManager.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Departure> findAll(boolean fetchStations, boolean fetchTickets, boolean orderByDateTimeFrom) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Departure> query = builder.createQuery(Departure.class);
        Root<Departure> from = query.from(Departure.class);

        findDeparture(builder, query, from, fetchStations, fetchTickets, orderByDateTimeFrom);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Departure> findAfterDateTime(LocalDateTime dateTime, boolean fetchStations, boolean fetchTickets, boolean orderByDateTimeFrom) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Departure> query = builder.createQuery(Departure.class);
        Root<Departure> from = query.from(Departure.class);

        findDeparture(builder, query, from, fetchStations, fetchTickets, orderByDateTimeFrom);
        query.where(builder.greaterThanOrEqualTo(from.get(Departure_.dateTimeFrom), dateTime));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Departure findById(Integer id, boolean fetchStations, boolean fetchTickets) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Departure> query = builder.createQuery(Departure.class);
        Root<Departure> from = query.from(Departure.class);

        findDeparture(builder, query, from, fetchStations, fetchTickets, false);

        query.where(builder.equal(from.get(Departure_.id), id));

        try {
            return entityManager.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Departure> findDateTimeFromBetweenOrDateTimeToBetween(LocalDateTime dateTimeFromLeft, LocalDateTime dateTimeFromRight,
                                                                      LocalDateTime dateTimeToLeft, LocalDateTime dateTimeToRight,
                                                                      boolean fetchStations, boolean fetchTickets, boolean orderByDateTimeFrom) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Departure> query = builder.createQuery(Departure.class);
        Root<Departure> from = query.from(Departure.class);
        query.select(from)
                .where(builder
                        .or(builder.and(
                                builder.greaterThanOrEqualTo(from.get(Departure_.dateTimeFrom), dateTimeFromLeft),
                                builder.lessThanOrEqualTo(from.get(Departure_.dateTimeFrom), dateTimeFromRight)
                        ), builder.and(
                                builder.greaterThanOrEqualTo(from.get(Departure_.dateTimeTo), dateTimeToLeft),
                                builder.lessThanOrEqualTo(from.get(Departure_.dateTimeTo), dateTimeToRight)
                        )));
        findDeparture(builder, query, from, fetchStations, fetchTickets, orderByDateTimeFrom);
        return entityManager.createQuery(query).getResultList();
    }

    private void findDeparture(CriteriaBuilder builder, CriteriaQuery<Departure> query,
                               Root<Departure> from, boolean fetchStations, boolean fetchTickets,
                               boolean orderByDateTimeFrom) {
        if (fetchStations) {
            from.fetch(Departure_.stationFrom);
            from.fetch(Departure_.stationTo);
        }
        if (fetchTickets) {
            from.fetch(Departure_.coaches).fetch(Coach_.tickets);
            query.distinct(true); // returns many duplicates
        }
        if (orderByDateTimeFrom) {
            query.orderBy(builder.asc(from.get(Departure_.dateTimeFrom)));
        }
    }
}
