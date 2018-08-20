package javaschool.dao.impl;

import java.util.List;
import javaschool.dao.api.UserDAO;
import javaschool.entity.User;
import javaschool.entity.User_;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl extends GenericAbstractDAO<User, Integer> implements UserDAO {
    @Override
    public User findByUsername(String username) {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> userRoot = query.from(User.class);
            Predicate usernameEqual = builder.equal(userRoot.get(User_.username), username);

            return entityManager.createQuery(query.select(userRoot).where(usernameEqual)).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> from = query.from(User.class);
            Predicate emailEq = builder.equal(from.get(User_.email), email);

            return entityManager.createQuery(query.select(from).where(emailEq)).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<User> findByUsernameOrEmail(String username, String email) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> from = query.from(User.class);
        Predicate usernameOrEmailEq = builder.or(builder.equal(from.get(User_.username), username),
                builder.equal(from.get(User_.email), email));

        return entityManager.createQuery(query.select(from).where(usernameOrEmailEq))
                .getResultList();
    }
}
