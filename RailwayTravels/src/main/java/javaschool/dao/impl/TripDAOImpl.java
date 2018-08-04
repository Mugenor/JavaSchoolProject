package javaschool.dao.impl;

import java.util.List;
import javaschool.dao.api.TripDAO;
import javaschool.entity.Departure;
import javaschool.entity.Departure_;
import javaschool.entity.Station_;
import javaschool.entity.Trip;
import javaschool.entity.Trip_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Root;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Repository;

@Repository
public class TripDAOImpl extends GenericAbstractDAO<Trip, Integer> implements TripDAO {

    @Override
    public List<Trip> findAllAfter(LocalDateTime dateTime) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trip> query = builder.createQuery(Trip.class);
        Root<Trip> from = query.from(Trip.class);
        ListJoin<Trip, Departure> departureListJoin = from.join(Trip_.departures);
        from.fetch(Trip_.departures);
        query.select(from).where(
                builder.greaterThanOrEqualTo(departureListJoin.get(Departure_.dateTimeFrom), dateTime)
        );
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Trip> findDateTimeFromBetweenBetweenOrDateTimeToBetween(LocalDateTime begin, LocalDateTime end) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trip> query = builder.createQuery(Trip.class);
        Root<Trip> from = query.from(Trip.class);
        ListJoin<Trip, Departure> departureListJoin = from.join(Trip_.departures);
        from.fetch(Trip_.departures);
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
        );
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Trip> findByDepartures(List<Departure> departures) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trip> query = builder.createQuery(Trip.class);
        Root<Departure> from = query.from(Departure.class);
        from.in(departures);
        query.select(from.join(Departure_.trip));
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
