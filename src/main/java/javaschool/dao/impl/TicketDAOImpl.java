package javaschool.dao.impl;

import javaschool.dao.api.TicketDAO;
import javaschool.entity.Coach;
import javaschool.entity.Departure;
import javaschool.entity.Ticket;
import javaschool.entity.Ticket_;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class TicketDAOImpl extends GenericAbstractDAO<Ticket, Integer> implements TicketDAO {
    @Override
    public Ticket findTicketBySitNumAndCoach(Integer siteNum, Coach coach) {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Ticket> query = builder.createQuery(Ticket.class);
            Root<Ticket> from = query.from(Ticket.class);
            Predicate siteNumEq = builder.equal(from.get(Ticket_.siteNum), siteNum);
            Predicate coachEq = builder.equal(from.get(Ticket_.coach), coach);
            return entityManager.createQuery(query.where(coachEq, siteNumEq)).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
