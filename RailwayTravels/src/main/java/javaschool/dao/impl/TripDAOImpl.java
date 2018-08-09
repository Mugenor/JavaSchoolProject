package javaschool.dao.impl;

import java.util.List;
import javaschool.dao.api.TripDAO;
import javaschool.entity.Departure;
import javaschool.entity.Departure_;
import javaschool.entity.Passenger;
import javaschool.entity.Station;
import javaschool.entity.Station_;
import javaschool.entity.Trip;
import javaschool.entity.Trip_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Repository;

@Repository
public class TripDAOImpl extends GenericAbstractDAO<Trip, Integer> implements TripDAO {

    @Override
    public List<Trip> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trip> query = builder.createQuery(Trip.class);
        Root<Trip> from = query.from(Trip.class);
        Fetch<Trip, Departure> departureFetch = from.fetch(Trip_.departures);
        departureFetch.fetch(Departure_.stationFrom);
        departureFetch.fetch(Departure_.stationTo);
        query.select(from).distinct(true);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Trip> findAllAfter(LocalDateTime dateTime) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trip> query = builder.createQuery(Trip.class);
        Root<Trip> from = query.from(Trip.class);
        ListJoin<Trip, Departure> departureListJoin = from.join(Trip_.departures);
        Fetch<Trip, Departure> departureFetch = from.fetch(Trip_.departures);
        departureFetch.fetch(Departure_.stationFrom);
        departureFetch.fetch(Departure_.stationTo);
        query.select(from).where(
                builder.greaterThanOrEqualTo(departureListJoin.get(Departure_.dateTimeFrom), dateTime)
        ).distinct(true);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Trip> findDateTimeFromBetweenBetweenOrDateTimeToBetween(LocalDateTime begin, LocalDateTime end) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trip> query = builder.createQuery(Trip.class);
        Root<Trip> from = query.from(Trip.class);
        ListJoin<Trip, Departure> departureListJoin = from.join(Trip_.departures);
        Fetch<Trip, Departure> departureFetch = from.fetch(Trip_.departures);
        departureFetch.fetch(Departure_.stationFrom);
        departureFetch.fetch(Departure_.stationTo);
        query.select(from).where(
                builder.or(
                        builder.and(
                                builder.greaterThanOrEqualTo(departureListJoin.get(Departure_.dateTimeFrom), begin),
                                builder.lessThanOrEqualTo(departureListJoin.get(Departure_.dateTimeFrom), end)
                        ),
                        builder.and(
                                builder.greaterThanOrEqualTo(departureListJoin.get(Departure_.dateTimeFrom), begin),
                                builder.lessThanOrEqualTo(departureListJoin.get(Departure_.dateTimeFrom), end)
                        )
                )
        ).distinct(true);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Trip> findByStationTitleToDateTimeToBetween(String stationTitleTo, LocalDateTime dateTimeLeftBound, LocalDateTime dateTimeRightBound) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trip> query = builder.createQuery(Trip.class);
        Root<Departure> from = query.from(Departure.class);
        query.select(from.join(Departure_.trip)).where(
                builder.and(
                        builder.equal(from.join(Departure_.stationTo).get(Station_.title), stationTitleTo),
                        builder.greaterThanOrEqualTo(from.get(Departure_.dateTimeTo), dateTimeLeftBound),
                        builder.lessThanOrEqualTo(from.get(Departure_.dateTimeTo), dateTimeRightBound)
                )
        );
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Trip> findByStationFromTitle(String stationFromTitle) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trip> query = builder.createQuery(Trip.class);
        Root<Trip> from = query.from(Trip.class);
        Join<Departure, Station> stationJoin = from.join(Trip_.departures).join(Departure_.stationFrom);
        Predicate stationFromTitlePredicate = builder.equal(stationJoin.get(Station_.title), stationFromTitle);
        Fetch<Trip, Departure> departureFetch = from.fetch(Trip_.departures);
        departureFetch.fetch(Departure_.stationFrom);
        departureFetch.fetch(Departure_.stationTo);
        query.select(from).where(stationFromTitlePredicate).distinct(true);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Trip> findByStationTitleFromDateTimeFromBetween(String stationTitleFrom, LocalDateTime dateTimeLeftBound, LocalDateTime dateTimeRightBound) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trip> query = builder.createQuery(Trip.class);
        Root<Departure> from = query.from(Departure.class);
        query.select(from.join(Departure_.trip)).where(
                builder.and(
                        builder.equal(from.join(Departure_.stationFrom).get(Station_.title), stationTitleFrom),
                        builder.greaterThanOrEqualTo(from.get(Departure_.dateTimeFrom), dateTimeLeftBound),
                        builder.lessThanOrEqualTo(from.get(Departure_.dateTimeFrom), dateTimeRightBound)
                )
        );
        return entityManager.createQuery(query).getResultList();
    }
}
