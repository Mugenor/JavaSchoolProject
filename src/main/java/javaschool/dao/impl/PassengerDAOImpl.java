package javaschool.dao.impl;

import javaschool.dao.api.PassengerDAO;
import javaschool.entity.*;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class PassengerDAOImpl extends GenericAbstractDAO<Passenger, Integer> implements PassengerDAO {
    @Override
    public Passenger findByNameAndSurnameAndBirthday(String name, String surname, LocalDate birthday) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Passenger> query = builder.createQuery(Passenger.class);
        Root<Passenger> from = query.from(Passenger.class);
        Predicate nameEq = builder.equal(from.get(Passenger_.name), name);
        Predicate surnameEq = builder.equal(from.get(Passenger_.surname), surname);
        Predicate birthdayEq = builder.equal(from.get(Passenger_.birthday), birthday);
        query.where(nameEq, surnameEq, birthdayEq);
        return entityManager.createQuery(query).getSingleResult();
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
