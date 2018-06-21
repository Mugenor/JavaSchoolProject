package javaschool.dao;

import javaschool.entity.Passenger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PassengerDAOImpl implements PassengerDAO {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Passenger passenger) {
        Session session = sessionFactory.getCurrentSession();
        session.save(passenger);
    }

    @SuppressWarnings("unchecked")
    public List<Passenger> list() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Passenger> passengers = session.createCriteria(Passenger.class).list();
        return passengers;
    }
}
