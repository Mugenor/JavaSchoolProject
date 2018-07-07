package javaschool.dao.impl;

import java.util.List;
import javaschool.dao.api.DepartureDAO;
import javaschool.entity.Departure;
import javaschool.entity.Departure_;
import javaschool.entity.Station;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Repository;

@Repository
public class DepartureDAOImpl extends GenericAbstractDAO<Departure, Integer> implements DepartureDAO {

    @Override
    public List<Departure> findFromToBetween(Station stFrom, Station stTo, LocalDateTime dateFrom, LocalDateTime dateTo) {
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
    public List<Departure> findAll(boolean fetchStations, boolean fetchTickets) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Departure> query = builder.createQuery(Departure.class);
        Root<Departure> from = query.from(Departure.class);
        if (fetchStations) {
            from.fetch(Departure_.stationTo);
            from.fetch(Departure_.stationFrom);
        }
        if(fetchTickets) {
            from.fetch(Departure_.tickets);
        }
        return entityManager.createQuery(query).getResultList();
    }
}
