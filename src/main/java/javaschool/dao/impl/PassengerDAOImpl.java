package javaschool.dao.impl;

import javaschool.dao.api.PassengerDAO;
import javaschool.entity.Passenger;
import org.springframework.stereotype.Repository;

@Repository
public class PassengerDAOImpl extends GenericAbstractDAO<Passenger, Integer> implements PassengerDAO {

}
