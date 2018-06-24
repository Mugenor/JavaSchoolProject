package javaschool.dao;

import javaschool.entity.Departure;
import javaschool.entity.Departure_;
import javaschool.entity.Station;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class DepartureDAOImpl extends GenericAbstractDAO<Departure, Integer> implements DepartureDAO {
    private StationDAO stationDAO;

    @Autowired
    public DepartureDAOImpl(StationDAO stationDAO) {
        this.stationDAO = stationDAO;
    }

    @Override
    public List<Departure> findFromToBetween(String stFrom, String stTo, LocalDateTime dateFrom, LocalDateTime dateTo) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        Station stationFrom = stationDAO.findByTitle(stFrom);
        Station stationTo = stationDAO.findByTitle(stTo);


        CriteriaQuery<Departure> query = builder.createQuery(Departure.class);
        Root<Departure> from = query.from(Departure.class);
        Predicate equalStationFrom = builder.equal(from.get(Departure_.stationFrom), stationFrom);
        Predicate equalStationTo = builder.equal(from.get(Departure_.stationTo), stationTo);
        Predicate greaterThanLeftDate = builder.greaterThan(from.get(Departure_.dateFrom), dateFrom);
        Predicate lessThanRightDate = builder.lessThan(from.get(Departure_.dateFrom), dateTo);
        Predicate resultPredicate = builder.and(equalStationFrom, equalStationTo, greaterThanLeftDate, lessThanRightDate);
        query.where(resultPredicate);

        return entityManager.createQuery(query).getResultList();
    }
}
