package javaschool.dao.impl;

import java.util.List;
import javaschool.dao.api.PassengerDAO;
import javaschool.entity.Departure;
import javaschool.entity.Departure_;
import javaschool.entity.Passenger;
import javaschool.entity.Passenger_;
import javaschool.entity.Ticket;
import javaschool.entity.Ticket_;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
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
        ListJoin<Departure, Ticket> ticketsJoin = departureRoot.join(Departure_.tickets);
        Join<Ticket, Passenger> passengerJoin = ticketsJoin.join(Ticket_.passenger);
        Predicate departureEq = builder.equal(departureRoot.get(Departure_.id), departureId);
        Predicate passengerNotNull = builder.isNotNull(ticketsJoin.get(Ticket_.passenger));

        return entityManager.createQuery(query.select(passengerJoin).where(departureEq, passengerNotNull)).getResultList();
    }
}
