package javaschool.dao;

import javaschool.entity.Ticket;
import org.springframework.stereotype.Repository;

@Repository
public class TicketDAOImpl extends GenericAbstractDAO<Ticket, Integer> implements TicketDAO {
}
