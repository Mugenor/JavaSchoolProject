package javaschool.dao.impl;

import javaschool.dao.api.StationDAO;
import javaschool.entity.Station;
import javaschool.entity.Station_;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class StationDAOImpl extends GenericAbstractDAO<Station, Integer> implements StationDAO {
    @Override
    public Station findByTitle(String title) {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Station> query = builder.createQuery(Station.class);
            Root<Station> from = query.from(Station.class);
            query.where(builder.equal(from.get(Station_.title), title));
            return entityManager.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
