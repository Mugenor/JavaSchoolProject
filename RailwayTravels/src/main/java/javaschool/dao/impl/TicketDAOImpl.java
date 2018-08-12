package javaschool.dao.impl;

import java.util.List;
import javaschool.dao.api.TicketDAO;
import javaschool.entity.Departure_;
import javaschool.entity.Passenger;
import javaschool.entity.Ticket;
import javaschool.entity.Ticket_;
import javaschool.entity.Trip_;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Repository;

@Repository
public class TicketDAOImpl extends GenericAbstractDAO<Ticket, Integer> implements TicketDAO {
    @Override
    public Ticket findByTripIdAndPassenger(Integer tripId, Passenger passenger) {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Ticket> query = builder.createQuery(Ticket.class);
            Root<Ticket> ticketRoot = query.from(Ticket.class);
            query.select(ticketRoot).where(
                    builder.and(
                            builder.equal(ticketRoot.join(Ticket_.trip).get(Trip_.id), tripId),
                            builder.equal(ticketRoot.get(Ticket_.passenger), passenger)
                    )
            );
            return entityManager.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Ticket> findByPassengerAndAfter(Passenger passenger, LocalDateTime dateTimeFrom) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ticket> query = builder.createQuery(Ticket.class);
        Root<Ticket> ticketRoot = query.from(Ticket.class);
        query.select(ticketRoot).where(
                builder.and(
                        builder.equal(ticketRoot.join(Ticket_.passenger), passenger),
                        builder.lessThan(ticketRoot.join(Ticket_.from).get(Departure_.dateTimeFrom), dateTimeFrom)
                )
        );
        ticketRoot.fetch(Ticket_.from);
        ticketRoot.fetch(Ticket_.to);
        return entityManager.createQuery(query).getResultList();
    }
}
