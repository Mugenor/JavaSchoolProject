package javaschool.dao.impl;

import java.util.List;
import javaschool.dao.api.DepartureDAO;
import javaschool.entity.Departure;
import javaschool.entity.Departure_;
import javaschool.entity.Trip;
import javaschool.entity.Trip_;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

/**
 * The type Departure dao.
 */
@Repository
public class DepartureDAOImpl extends GenericAbstractDAO<Departure, Integer> implements DepartureDAO {

    @Override
    public List<Departure> findAll(boolean fetchStations, boolean orderByDateTimeFrom) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Departure> query = builder.createQuery(Departure.class);
        Root<Departure> from = query.from(Departure.class);

        findDeparture(builder, query, from, fetchStations, orderByDateTimeFrom);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Departure findById(Integer id, boolean fetchStations) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Departure> query = builder.createQuery(Departure.class);
        Root<Departure> from = query.from(Departure.class);

        findDeparture(builder, query, from, fetchStations, false);

        query.where(builder.equal(from.get(Departure_.id), id));

        try {
            return entityManager.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


    @Override
    public List<Departure> findByTripIdAndNumberInTripBetween(Integer tripId, Integer leftBound, Integer rightBound,
                                                              boolean fetchStations, boolean orderByDateTimeFrom) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Departure> query = builder.createQuery(Departure.class);
        Root<Departure> from = query.from(Departure.class);
        Join<Departure, Trip> tripJoin = from.join(Departure_.trip);
        query.select(from).where(
                builder.and(
                        builder.equal(tripJoin.get(Trip_.id), tripId),
                        builder.greaterThanOrEqualTo(from.get(Departure_.numberInTrip), leftBound),
                        builder.lessThanOrEqualTo(from.get(Departure_.numberInTrip), rightBound)
                )
        );
        findDeparture(builder, query, from, fetchStations, orderByDateTimeFrom);
        return entityManager.createQuery(query).getResultList();
    }


    private void findDeparture(CriteriaBuilder builder, CriteriaQuery<Departure> query,
                               Root<Departure> from, boolean fetchStations,
                               boolean orderByDateTimeFrom) {
        if (fetchStations) {
            from.fetch(Departure_.stationFrom);
            from.fetch(Departure_.stationTo);
        }
        if (orderByDateTimeFrom) {
            query.orderBy(builder.asc(from.get(Departure_.dateTimeFrom)));
        }
    }
}
