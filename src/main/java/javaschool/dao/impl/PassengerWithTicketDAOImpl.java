package javaschool.dao.impl;

import javaschool.dao.api.PassengerWithTicketDAO;
import javaschool.entity.Passenger;
import javaschool.entity.PassengerWithTicket;
import javaschool.entity.Ticket;
import javaschool.entity.id.PassengerTicketId;
import org.springframework.stereotype.Repository;

@Repository
public class PassengerWithTicketDAOImpl extends GenericAbstractDAO<PassengerWithTicket, PassengerTicketId> implements PassengerWithTicketDAO {
    @Override
    public void save(Passenger passenger, Ticket ticket) {
        save(new PassengerWithTicket(new PassengerTicketId(passenger, ticket)));
    }
}
