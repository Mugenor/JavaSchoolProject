package javaschool.dao;

import javaschool.entity.Passenger;
import javaschool.service.PassengerService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.criteria.CriteriaBuilderImpl;
import org.hibernate.jpa.criteria.CriteriaQueryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class PassengerDAOImpl implements PassengerDAO {
    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Passenger passenger) {
        entityManager.persist(passenger);
    }

    @SuppressWarnings("unchecked")
    public List<Passenger> list() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Passenger> criteriaQuery = criteriaBuilder.createQuery(Passenger.class);
        criteriaQuery.select(criteriaQuery.from(Passenger.class));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
