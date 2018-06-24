package javaschool.service;

import javaschool.dao.GenericAbstractDAO;
import javaschool.entity.Ticket;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl extends GenericAbstractDAO<Ticket, Integer> implements TicketService {
}
