package javaschool.dao.impl;

import javaschool.dao.api.PassengerDAO;
import javaschool.entity.Passenger;
import javaschool.entity.Passenger_;
import javaschool.entity.Ticket;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
    public void buyTicket(Passenger passenger, Ticket ticket) {

    }
}
