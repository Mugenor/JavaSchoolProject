package javaschool.dao.impl;

import java.util.List;
import javaschool.dao.api.DepartureDAO;
import javaschool.entity.Coach;
import javaschool.entity.Coach_;
import javaschool.entity.Departure;
import javaschool.entity.Departure_;
import javaschool.entity.Station;
import javaschool.entity.Ticket;
import javaschool.entity.Ticket_;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Order;
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

        findDeparture(builder, query, from, fetchStations, fetchTickets);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Departure findById(Integer id, boolean fetchStations, boolean fetchTickets) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Departure> query = builder.createQuery(Departure.class);
        Root<Departure> from = query.from(Departure.class);

        findDeparture(builder, query, from, fetchStations, fetchTickets);

        query.where(builder.equal(from.get(Departure_.id), id));

        return entityManager.createQuery(query).getSingleResult();
    }

    private void findDeparture(CriteriaBuilder builder, CriteriaQuery<Departure> query,
                                                   Root<Departure> from, boolean fetchStations, boolean fetchTickets){
        if (fetchStations) {
            from.fetch(Departure_.stationTo);
            from.fetch(Departure_.stationFrom);
        }

        if(fetchTickets) {
            ListJoin<Departure, Coach> coachesJoin = from.join(Departure_.coaches);
            ListJoin<Coach, Ticket> ticketsJoin = coachesJoin.join(Coach_.tickets);
            query.orderBy(builder.asc(ticketsJoin.get(Ticket_.siteNum)));
        }
    }
}
