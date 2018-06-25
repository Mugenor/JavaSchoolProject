package javaschool.dao.impl;

import javaschool.dao.api.TicketDAO;
import javaschool.entity.Departure;
import javaschool.entity.Ticket;
import javaschool.entity.Ticket_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class TicketDAOImpl extends GenericAbstractDAO<Ticket, Integer> implements TicketDAO {
    @Override
    public Ticket findTicketBySitNumAndDeparture(Integer siteNum, Departure departure) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ticket> query = builder.createQuery(Ticket.class);
        Root<Ticket> from = query.from(Ticket.class);
        Predicate siteNumEq = builder.equal(from.get(Ticket_.siteNum), siteNum);
        Predicate departureEq = builder.equal(from.get(Ticket_.departure), departure);
        return entityManager.createQuery(query.where(departureEq, siteNumEq)).getSingleResult();
    }
}
