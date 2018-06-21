package java.school.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.school.entity.Passenger;
import java.util.List;

public class PassengerImpl implements PassengerDAO {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Passenger passenger) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(passenger);
        tx.commit();
        session.close();
    }

    public List<Passenger> list() {
        Session session = this.sessionFactory.openSession();
        List<Passenger> passengers = session.createQuery("from Passenger").list();
        session.close();
        return passengers;
    }
}
